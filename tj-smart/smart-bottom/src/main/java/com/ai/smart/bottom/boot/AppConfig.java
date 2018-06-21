package com.ai.smart.bottom.boot;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ConfigurationProperties
@PropertySources({
    @PropertySource("classpath:props/${user.env}/trans.properties"),
    @PropertySource("classpath:props/${user.env}/MsgConfig.properties")
})
public class AppConfig {

}
