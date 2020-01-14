package com.qding.hk.rest.patrol.bean.response;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by libo on 17-11-9.
 */
@ApiModel(description = "response obj",value = "response obj")
public class HttpResponse<T> {
    @ApiModelProperty(value = "response coe",name = "code")
    private Integer code;
    @ApiModelProperty(value = "response data",name = "data")
    private T data;
    @ApiModelProperty(value = "response msg",name="msg")
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = HttpCodeEnum.SUCCESS.getCode();
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public static <T> HttpResponse<T> createSuccess(T data) {
        HttpResponse<T> response = new HttpResponse<>();
        response.setData(data);
        response.setCode(200);
        return response;
    }

    public static <T> HttpResponse<T> createFail(Integer code, String msg) {
        HttpResponse<T> response = new HttpResponse<>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }
}
