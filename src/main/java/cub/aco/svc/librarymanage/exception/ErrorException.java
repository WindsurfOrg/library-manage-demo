package cub.aco.svc.librarymanage.exception;

import cub.aco.svc.librarymanage.enums.ErrorCode;
import lombok.Getter;


@Getter
public class ErrorException extends RuntimeException {

    final private ErrorCode code;

    final private String message;

    public ErrorException(ErrorCode code) {
        super(code.getCode());
        this.code = code;
        this.message = code.getDescription();
    }
}
