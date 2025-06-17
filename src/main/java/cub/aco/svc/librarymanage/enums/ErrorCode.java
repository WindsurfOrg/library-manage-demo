package cub.aco.svc.librarymanage.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    DATA_EXISTS("B001", "資料已存在"),
    DATA_NOT_FOUND("B002", "資料不存在"),

    BOOK_BORROWED("L001", "該書已借出"),
    BOOK_LOST("L002", "該書遺失或損毁"),
    BOOK_RETURNED("L003", "該書已歸還");


    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private final String code;
    private final String description;

}
