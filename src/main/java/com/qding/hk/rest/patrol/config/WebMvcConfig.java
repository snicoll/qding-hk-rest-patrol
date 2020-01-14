package com.qding.hk.rest.patrol.config;

import com.qding.hk.rest.common.config.QdWebCommonConfig;
import com.qding.hk.rest.common.interceptor.CrossDomainInterceptor;
import com.qding.hk.rest.common.interceptor.DecryptInterceptor;
import com.qding.hk.rest.common.interceptor.UserTokenInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@ComponentScan(basePackages = {"com.qding.hk.rest.common,com.qding.hk.rest.patrol"})
public class WebMvcConfig extends QdWebCommonConfig
{

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Autowired
    CrossDomainInterceptor crossDomainInterceptor;

    @Autowired
    UserTokenInterceptor userTokenInterceptor;

    @Autowired
    MappingJackson2HttpMessageConverter converter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(crossDomainInterceptor);
        registry.addInterceptor(new DecryptInterceptor());
        registry.addInterceptor(userTokenInterceptor)
                .excludePathPatterns(new String[]{"/apiconsole*/**"});
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("apiconsole.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/jstree/**")
                .addResourceLocations("classpath:/META-INF/resources/jstree/");

        super.addResourceHandlers(registry);
    }

}
