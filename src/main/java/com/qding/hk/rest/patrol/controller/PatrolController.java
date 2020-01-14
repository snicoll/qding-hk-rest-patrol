package com.qding.hk.rest.patrol.controller;

import com.alibaba.fastjson.JSONObject;
import com.qding.hk.rest.common.argument.ArgumentResolve;
import com.qding.hk.rest.common.base.QdingAppData;
import com.qding.hk.rest.common.base.QdingRequestBodyT;
import com.qding.hk.rest.patrol.bean.RestTemplateUtils;
import com.qding.hk.rest.patrol.bean.request.LoginRequest;
import com.qding.hk.rest.patrol.bean.response.BaseResponse;
import com.qding.hk.rest.patrol.bean.response.HttpCodeEnum;
import com.qding.hk.rest.patrol.bean.response.HttpResponse;
import com.qding.hk.rest.patrol.config.DomainConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liaoly
 * @date 2018/9/24  13:26
 * @param:
 * @return
 */

@RestController
@RequestMapping("/agent/")
@Slf4j
public class PatrolController {

    Logger logger = LoggerFactory.getLogger(PatrolController.class);

    @Autowired
    private HttpServletRequest hRequest;


    @RequestMapping(value = "**")
    public QdingAppData<BaseResponse> getViewList(
            @ArgumentResolve QdingRequestBodyT<LoginRequest> request) {
        QdingAppData data;
        HttpResponse<Object> response = null;
        String url = hRequest.getRequestURL().toString();
        Integer sourceSystem = request.getBody().getSourceSystem();
        int isJson = request.getBody().getIsJson();
        String method = hRequest.getMethod();
        url = DomainConfig.getDomain(sourceSystem) + url.substring(url.indexOf("agent/") + 5);
        log.info("url:{}", url);
        HttpMethod httpMethod = this.getHttpMethod(method);
        String s;
        try {
            switch (httpMethod) {
                case GET:
                    s = RestTemplateUtils.get(url, new JSONObject());
                    response = JSONObject.parseObject(s, HttpResponse.class);
                    break;
                case POST:
                    if (isJson == 0) {
                        s = RestTemplateUtils.post(url, JSONObject.parseObject(request.getBody().getMyParam()), MediaType.APPLICATION_FORM_URLENCODED);
                        response = JSONObject.parseObject(s, HttpResponse.class);
                    } else {
                        s = RestTemplateUtils.post(url, JSONObject.parseObject(request.getBody().getMyParam()), MediaType.APPLICATION_JSON);
                        response = JSONObject.parseObject(s, HttpResponse.class);
                    }
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            logger.error("getViewList error",e);
            data = QdingAppData.error(404, "request error");
            return data;
        }
        if (response == null) {
            logger.error("getViewList response is null");
            data = QdingAppData.error(601, "request empty");
        } else if (response.getCode().intValue() == HttpCodeEnum.SUCCESS.getCode().intValue()) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(response.getData());
            baseResponse.setToast("");
            data = QdingAppData.ok(baseResponse);
        } else {
            data = QdingAppData.error(response.getCode(), response.getMsg());
        }
        return data;


    }

    private HttpMethod getHttpMethod(String method) {

        switch (method.toUpperCase()) {
            case "GET":
                return HttpMethod.GET;
            case "POST":
                return HttpMethod.POST;
            case "PUT":
                return HttpMethod.PUT;
            case "DELETE":
                return HttpMethod.DELETE;
            case "PATCH":
                return HttpMethod.PATCH;
            case "OPTIONS":
                return HttpMethod.OPTIONS;
            case "TRACE":
                return HttpMethod.TRACE;
            default:
                return null;
        }
    }


}
