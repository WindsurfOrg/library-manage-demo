package cub.aco.svc.librarymanage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class QueryOverdueBookRequest {

    @Min(value = 1, message = "需為整數" )
    @NotNull(message = "必填")
    @JsonProperty("overdue")
    private Integer overdue;
}
