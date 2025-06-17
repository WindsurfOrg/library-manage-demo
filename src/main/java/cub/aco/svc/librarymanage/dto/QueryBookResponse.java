package cub.aco.svc.librarymanage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
public class QueryBookResponse {

    @JsonProperty("MWHEADER")
    private ActionDetail mwHeader;

    @JsonProperty("book_count")
    private Integer bookCount;
    
    @JsonProperty("book_list")
    private List<BookInfo> bookList;

}
