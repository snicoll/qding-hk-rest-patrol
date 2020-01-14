package com.qding.hk.rest.patrol.config;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: libo
 * @Date: 2018/12/3 14:57
 * @Version 1.0
 */
@Configuration
public class DomainConfig {
    @Value("${source.system.domain}")
    private String sourceSystemDomain;

    private static Map<Integer, String> domainMap;

    @PostConstruct
    public void init() {
        domainMap = new HashMap<>();
        if (StringUtils.isNotEmpty(sourceSystemDomain)) {
            String[] sourceSytem = sourceSystemDomain.split(";");
            for (String s : sourceSytem) {
                if (StringUtils.isNotEmpty(s)) {
                    String[] ss = s.split("@@");
                    domainMap.put(Integer.parseInt(ss[0]), ss[1]);
                }
            }
        }
    }

    public static String getDomain(Integer source){
        return DomainConfig.domainMap.get(source);
    }
}
