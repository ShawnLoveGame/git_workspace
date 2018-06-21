package com.ai.smart.third.boot;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ConfigurationProperties
@PropertySources({
        @PropertySource("classpath:props/${user.env}/redis.properties"),
        @PropertySource("classpath:props/${user.env}/mysql.properties"),
        @PropertySource("classpath:props/${user.env}/domain.properties")
})
public class AppConfig {

}
