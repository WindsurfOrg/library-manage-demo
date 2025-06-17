package cub.aco.svc.librarymanage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import cub.aco.svc.librarymanage.enums.BookStatus;
import cub.aco.svc.librarymanage.enums.Language;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class BookInfo {

    @JsonProperty("book_ISBN")
    private String bookIsbn;

    @JsonProperty("book_language")
    private Language bookLanguage;

    @JsonProperty("book_name")
    private String bookName;

    @JsonProperty("book_author")
    private String bookAuthor;

    @JsonProperty("book_publisher")
    private String bookPublisher;

    @JsonProperty("book_pub_date")
    private LocalDate bookPubDate;

    @JsonProperty("book_create_date")
    private LocalDate bookCreateDate;

    @JsonProperty("book_status")
    private BookStatus bookStatus;

    @JsonProperty("book_borrower_ID")
    private String bookBorrowerId;

    @JsonProperty("borrow_date")
    private LocalDate borrowDate;

    @JsonSetter(nulls = Nulls.SKIP)
    @JsonProperty("overdue_days")
    private Long overdueDays;
}
