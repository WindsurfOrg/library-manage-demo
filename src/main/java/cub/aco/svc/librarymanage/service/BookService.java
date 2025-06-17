package cub.aco.svc.librarymanage.service;

import cub.aco.svc.librarymanage.dto.*;
import cub.aco.svc.librarymanage.entity.Book;
import cub.aco.svc.librarymanage.entity.BorrowLog;
import cub.aco.svc.librarymanage.enums.BookStatus;
import cub.aco.svc.librarymanage.enums.BorrowAction;
import cub.aco.svc.librarymanage.enums.ErrorCode;
import cub.aco.svc.librarymanage.enums.Language;
import cub.aco.svc.librarymanage.exception.ErrorException;
import cub.aco.svc.librarymanage.repository.BookRepository;
import cub.aco.svc.librarymanage.repository.BorrowLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    final BookRepository bookRepository;
    final BorrowLogRepository borrowLogRepository;

    @Value("${library.borrow.day}")
    private Integer borrowDays;

    public void create(final AddBookRequest request) {
        final String id = request.getBookIsbn();

        bookRepository.findById(id).ifPresent(entity -> {throw new ErrorException(ErrorCode.DATA_EXISTS);});

        final Book book = Book.builder()
                .bookIsbn(id)
                .bookLanguage(request.getBookLanguage().getValue())
                .bookName(trimValue(request.getBookName()))
                .bookAuthor(trimValue(request.getBookAuthor()))
                .bookPublisher(trimValue(request.getBookPublisher()))
                .bookPubDate(request.getBookPubDate())
                .bookStatus(BookStatus.IN.getValue())
                .build();
        bookRepository.save(book);
    }

    private String trimValue(final String value) {
        return Optional.ofNullable(value).map(String::trim).orElse(null);
    }

    public void update(UpdateBookRequest request) {
        final String id = request.getBookIsbn();

        final Book entity = bookRepository.findById(id).orElseThrow(() -> new ErrorException(ErrorCode.DATA_NOT_FOUND));
        entity.setBookLanguage(request.getBookLanguage().getValue());
        entity.setBookName(trimValue(request.getBookName()));
        entity.setBookAuthor(trimValue(request.getBookAuthor()));
        entity.setBookPublisher(trimValue(request.getBookPublisher()));
        entity.setBookPubDate(request.getBookPubDate());
        entity.setBookCreateDate(request.getBookCreateDate());
        entity.setBookStatus(request.getBookStatus().getValue());
        bookRepository.save(entity);
    }

    public void delete(DeleteBookRequest request) {
        final String id = request.getBookIsbn();
        final Book entity = bookRepository.findById(id).orElseThrow(() -> new ErrorException(ErrorCode.DATA_NOT_FOUND));
        bookRepository.delete(entity);
    }

    public List<BookInfo> query(QueryBookRequest request) {
        List<Book> books = new ArrayList<>();
        switch(request.getQueryType()) {
            case BOOK_ISBN:
                final String id = StringUtils.isEmpty(request.getBookIsbn()) ? null : request.getBookIsbn();
                books = bookRepository.findAll(Example.of(Book.builder().bookIsbn(id).build()));
                break;
            case BOOK_STATUS:
                final String bookStatus = Optional.ofNullable(request.getBookStatus()).map(BookStatus::getValue).orElse(null);
                books = bookRepository.findAll(Example.of(Book.builder().bookStatus(bookStatus).build()));
                break;
            case BOOK_NAME:
                final String bookName = request.getBookName();
                books = bookRepository.findByBookNameContaining(bookName);
                break;

        }
        return books.stream().map(this::mapToBookInfo).collect(Collectors.toList());
    }

    private BookInfo mapToBookInfo(Book book) {
        return BookInfo.builder()
                .bookIsbn(book.getBookIsbn())
                .bookLanguage(Objects.requireNonNull(Language.of(book.getBookLanguage())))
                .bookName(book.getBookName())
                .bookAuthor(book.getBookAuthor())
                .bookPublisher(book.getBookPublisher())
                .bookPubDate(book.getBookPubDate())
                .bookCreateDate(book.getBookCreateDate())
                .bookStatus(Objects.requireNonNull(BookStatus.of(book.getBookStatus())))
                .bookBorrowerId(book.getBookBorrowerId())
                .borrowDate(book.getBorrowDate())
                .build();
    }

    @Transactional
    public void borrow(BorrowBookRequest request) {
        final String bookIsbn = request.getBookIsbn();
        final Book entity = bookRepository.findById(bookIsbn).orElseThrow(() -> new ErrorException(ErrorCode.DATA_NOT_FOUND));

        switch(Objects.requireNonNull(BookStatus.of(entity.getBookStatus()))) {
            case IN:
                entity.setBookStatus(BookStatus.OUT.getValue());
                entity.setBookBorrowerId(request.getBookBorrowerId());
                entity.setBorrowDate(LocalDate.now());
                bookRepository.save(entity);

                final BorrowLog borrowLog = BorrowLog.builder()
                        .borrowerId(request.getBookBorrowerId())
                        .borrowBookIsbn(bookIsbn)
                        .borrowAction(BorrowAction.BORROW.getValue())
                        .build();
                borrowLogRepository.save(borrowLog);
                break;
            case OUT:
                throw new ErrorException(ErrorCode.BOOK_BORROWED);
            case LOST:
                throw new ErrorException(ErrorCode.DATA_NOT_FOUND);
        }
    }

    public void returnBook(ReturnBookRequest request) {
        final String bookIsbn = request.getBookIsbn();
        final Book entity = bookRepository.findById(bookIsbn).orElseThrow(() -> new ErrorException(ErrorCode.DATA_NOT_FOUND));
        switch(Objects.requireNonNull(BookStatus.of(entity.getBookStatus()))) {
            case IN:
                throw new ErrorException(ErrorCode.BOOK_RETURNED);
            case OUT:
                entity.setBookStatus(BookStatus.IN.getValue());
                entity.setBookBorrowerId(null);
                entity.setBorrowDate(null);
                bookRepository.save(entity);

                final BorrowLog borrowLog = BorrowLog.builder()
                        .borrowerId(request.getBookBorrowerId())
                        .borrowBookIsbn(bookIsbn)
                        .borrowAction(BorrowAction.RETURN.getValue())
                        .build();
                borrowLogRepository.save(borrowLog);
                break;
            case LOST:
                throw new ErrorException(ErrorCode.DATA_NOT_FOUND);
        }
    }

    public List<BookInfo> queryOverdueBooks(QueryOverdueBookRequest request) {
        final LocalDate referenceDate = LocalDate.now().minusDays(borrowDays -1 + request.getOverdue() -1);

        return bookRepository.findByBookStatusAndBorrowDateBeforeOrderByBorrowDateAscBookBorrowerId(BookStatus.OUT.getValue(), referenceDate)
                .stream()
                .map(this::mapToBookInfo)
                .peek(data -> data.setOverdueDays(Duration.ofDays(DAYS.between(data.getBorrowDate(), referenceDate)).toDays()))
                .collect(Collectors.toList());
    }
}
