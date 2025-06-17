package cub.aco.svc.librarymanage.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



public enum Language {


    ZH("1"),

    CH("2"),

    EN("3");


    Language(String code) {
        this.code = code;
    }

    private final String code;

    @JsonValue
    public String getValue() {
        return this.code;
    }

    @JsonCreator
    public static Language of(String code) {
        for (Language language : Language.values()) {
            if (language.code.equals(code) ) {
                return language;
            }
        }
        return null;
    }
}
