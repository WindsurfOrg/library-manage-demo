package cub.aco.svc.librarymanage.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS("0000", "交易成功"),
    NOT_VALID("V001","缺乏必要輸入欄位:%s"),
    ERROR("E001","交易失敗, %s"),
    SYSTEM_ERROR("E999", "其他錯誤");


    ResponseCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private final String code;
    private final String description;

}
