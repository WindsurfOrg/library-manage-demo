package cub.aco.svc.librarymanage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActionDetail {

    @JsonProperty("RETURNCODE")
    private String returnCode;

    @JsonProperty("RETURNDESC")
    private String returnDesc;
}
