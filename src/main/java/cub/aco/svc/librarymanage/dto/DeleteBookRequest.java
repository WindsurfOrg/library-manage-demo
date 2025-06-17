package cub.aco.svc.librarymanage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class DeleteBookRequest {

    @Size(max = 30, message = "英數字{max}位")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "英數字30位")
    @NotBlank(message = "必填")
    @JsonProperty("book_ISBN")
    private String bookIsbn;
}
