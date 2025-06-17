package cub.aco.svc.librarymanage.repository;

import cub.aco.svc.librarymanage.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {


    List<Book> findByBookNameContaining(String bookName);

    List<Book> findByBookStatusAndBorrowDateBeforeOrderByBorrowDateAscBookBorrowerId(String bookStatus, LocalDate borrowDate);

}
