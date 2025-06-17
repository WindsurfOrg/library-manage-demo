package cub.aco.svc.librarymanage.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum QueryType {

    BOOK_ISBN("1"),

    BOOK_STATUS("2"),

    BOOK_NAME("3");


    QueryType(String code) {
        this.code = code;
    }

    private final String code;

    @JsonValue
    public String getValue() {
        return this.code;
    }

    @JsonCreator
    public static QueryType of(String code) {
        for (QueryType queryType : QueryType.values()) {
            if (queryType.code.equals(code) ) {
                return queryType;
            }
        }
        return null;
    }

}
