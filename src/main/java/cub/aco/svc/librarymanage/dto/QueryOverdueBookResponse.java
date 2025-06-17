package cub.aco.svc.librarymanage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class QueryOverdueBookResponse {

    @JsonProperty("MWHEADER")
    private ActionDetail mwHeader;

    @JsonProperty("query_count")
    private Integer queryCount;

    @JsonProperty("query_list")
    private List<BookInfo> queryList;
}
