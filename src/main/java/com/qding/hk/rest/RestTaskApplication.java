package com.qding.hk.rest;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableApolloConfig
@SpringBootApplication
public class RestTaskApplication{

    static Logger logger = LoggerFactory.getLogger(RestTaskApplication.class);

    public static void main(String[] args) {
        Config config = ConfigService.getAppConfig();

        System.setProperty("spring.devtools.restart.enabled", "false");
        System.setProperty("dubbo.application.logger","slf4j");


        SpringApplication.run(RestTaskApplication.class, args);

    }

}
