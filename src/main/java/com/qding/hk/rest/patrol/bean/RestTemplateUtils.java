package com.qding.hk.rest.patrol.bean;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: RestTemplateUtils.java
 * @Prject: sensorsdata
 * @Package: com.springboottest.sensorsdata.utils
 * @author: hujunzheng
 * @date: 2017-4-20 14:07:18
 * @version: V1.0
 */
public class RestTemplateUtils {
    private static final RestTemplate RESTTEMPLATE_DEFAULT = new RestTemplate();
    private static final RestTemplate RESTTEMPLATE_WITH_FormHttpMessageConverter = new RestTemplate();
    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("([^&=]+)(=+)([^&]+)?");

    static {
        RESTTEMPLATE_DEFAULT.setErrorHandler(new DefaultResponseErrorHandler());

        RESTTEMPLATE_WITH_FormHttpMessageConverter.getMessageConverters().add(new FormHttpMessageConverter());
        RESTTEMPLATE_WITH_FormHttpMessageConverter.getMessageConverters().add(new FormHttpMessageConverter());
    }
    /**
     * @ClassName: DefaultResponseErrorHandler
     * @author: hujunzheng
     * @date: 2017-4-20 14:15:27
     */
    private static class DefaultResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().value() != HttpServletResponse.SC_OK;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()));
            StringBuilder sb = new StringBuilder();
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            try {
                throw new Exception(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param url
     * @param params
     * @return
     * @Title: get
     * @author: hujunzheng
     * @return: String
     */
    public static String get(String url, JSONObject params) {
        String response = RESTTEMPLATE_DEFAULT.getForObject(expandURL(url, params.keySet()), String.class, params);
        return response;
    }

    /**
     * @param url
     * @param params
     * @param mediaType
     * @return
     * @Title: post
     * @author: hujunzheng
     * @return: String
     */
    public static String post(String url, JSONObject params, MediaType mediaType) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(mediaType);
        HttpEntity<JSONObject> requestEntity = (mediaType == MediaType.APPLICATION_JSON
                || mediaType == MediaType.APPLICATION_JSON_UTF8) ? new HttpEntity<JSONObject>(params, requestHeaders)
                : new HttpEntity<JSONObject>(null, requestHeaders);
        String result = (mediaType == MediaType.APPLICATION_JSON || mediaType == MediaType.APPLICATION_JSON_UTF8)
                ? RESTTEMPLATE_DEFAULT.postForObject(url, requestEntity, String.class)
                : RESTTEMPLATE_DEFAULT.postForObject(expandURL(url, params.keySet()), requestEntity, String.class, params);
        return result;
    }


    /**
     * @param url
     * @param params
     * @param mediaType
     * @param clz
     * @return
     * @Title: post
     * @author: hujunzheng
     * @return: String
     */
    public static <T> T post(String url, JSONObject params, MediaType mediaType, Class<T> clz) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(mediaType);

        HttpEntity<?> requestEntity = (
                mediaType == MediaType.APPLICATION_JSON
                        || mediaType == MediaType.APPLICATION_JSON_UTF8)
                ? new HttpEntity<JSONObject>(params, requestHeaders)
                : (mediaType == MediaType.APPLICATION_FORM_URLENCODED
                ? new HttpEntity<MultiValueMap>(createMultiValueMap(params), requestHeaders)
                : new HttpEntity<>(null, requestHeaders));

        T result = (mediaType == MediaType.APPLICATION_JSON || mediaType == MediaType.APPLICATION_JSON_UTF8)
                ? RESTTEMPLATE_WITH_FormHttpMessageConverter.postForObject(url, requestEntity, clz)
                : RESTTEMPLATE_WITH_FormHttpMessageConverter.postForObject(mediaType == MediaType.APPLICATION_FORM_URLENCODED
                ? url
                : expandURL(url, params.keySet()), requestEntity, clz, params);

        return result;
    }

    private static MultiValueMap<String, String> createMultiValueMap(JSONObject params) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (String key : params.keySet()) {
            if (params.get(key) instanceof List) {
                for (Iterator<String> it = ((List<String>) params.get(key)).iterator(); it.hasNext(); ) {
                    String value = it.next();
                    map.add(key, value);
                }
            } else {
                map.add(key, params.getString(key));
            }
        }
        return map;
    }

    /**
     * @param url
     * @param keys
     * @return
     * @Title: expandURL
     * @author: hujunzheng
     * @Description: TODO
     * @return: String
     */
    private static String expandURL(String url, Set<?> keys) {
        Matcher mc = QUERY_PARAM_PATTERN.matcher(url);
        StringBuilder sb = new StringBuilder(url);
        if (mc.find()) {
            sb.append("&");
        } else {
            sb.append("?");
        }

        for (Object key : keys) {
            sb.append(key).append("=").append("{").append(key).append("}").append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}