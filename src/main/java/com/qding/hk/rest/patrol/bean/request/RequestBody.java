package com.qding.hk.rest.patrol.bean.request;

import com.qding.hk.rest.common.base.QdingRequestBodyT;
import lombok.Data;

/**
 * @Description:
 * @Auther: libo
 * @Date: 2018/11/29 19:00
 * @Version 1.0
 */
@Data
public class RequestBody<T> extends QdingRequestBodyT<T> {
    private String domain;
    private Integer isjson;
}
