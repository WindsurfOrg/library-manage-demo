package cub.aco.svc.librarymanage.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum BookStatus {


    IN("1"),

    OUT("2"),

    LOST("3");


    BookStatus(String code) {
        this.code = code;
    }

    private final String code;

    @JsonValue
    public String getValue() {
        return this.code;
    }

    @JsonCreator
    public static BookStatus of(String code) {
        for (BookStatus bookStatus : BookStatus.values()) {
            if (bookStatus.code.equals(code) ) {
                return bookStatus;
            }
        }
        return null;
    }

}
