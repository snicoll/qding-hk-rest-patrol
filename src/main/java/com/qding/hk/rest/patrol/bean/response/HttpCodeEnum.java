package com.qding.hk.rest.patrol.bean.response;

/**
 * Created by libo on 17-11-9.
 */
public enum HttpCodeEnum {
    SUCCESS(200, "success"),
    PARAM_ERROR(400, "parm error"),
    AUTH_ERROR(401, "no auth"),
    MEDIA_ERROR(415, "auth delay"),
    SERVER_ERROR(500, "server error"),
    UNKOWN_ERROR(700, "unkown error");
    private Integer code;
    private String desc;

    private HttpCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
