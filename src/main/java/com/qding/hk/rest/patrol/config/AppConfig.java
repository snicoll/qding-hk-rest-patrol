package com.qding.hk.rest.patrol.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author : cbd
 * @Date: 2018/10/25 11:05
 * @Description:
 */
@Configuration
@EnableApolloConfig
public class AppConfig {


    @Value("${timeout:2000}")
    private Integer timeout;

    @Value("${appname:100}")
    private String appName;

    @Value("${content:200}")
    private String content;

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
