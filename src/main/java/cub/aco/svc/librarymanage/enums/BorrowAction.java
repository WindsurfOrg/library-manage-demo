package cub.aco.svc.librarymanage.enums;

public enum BorrowAction {

    BORROW("1"),

    RETURN("2");


    BorrowAction(String code) {
        this.code = code;
    }

    private final String code;

    public String getValue() {
        return this.code;
    }

}
