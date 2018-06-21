package com.ai.smart.bottom.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

@Slf4j
@EnableWebMvc
@Configuration
public class OkafuConfigurerAdapter extends WebMvcConfigurerAdapter {


    @Resource
    public OkafuInterceptor okafuInterceptor;

    private String[] ignorUrls;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/qrcode/**").addResourceLocations("file:/cqwt/qrcode/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        init();
        registry.addInterceptor(okafuInterceptor).excludePathPatterns(ignorUrls);
        super.addInterceptors(registry);
    }


    public void init() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("web-pattern.yaml")) {
            Constructor constructor = new Constructor(WebPattern.class);
            TypeDescription webPatternsDescription = new TypeDescription(WebPattern.class);
            webPatternsDescription.putListPropertyType("antPatterns", String.class);
            constructor.addTypeDescription(webPatternsDescription);
            Yaml yaml = new Yaml(constructor);
            WebPattern webPatterns = (WebPattern) yaml.load(input);
            ignorUrls = webPatterns.getAntPatterns().toArray(new String[0]);
        } catch (Exception e) {
            log.error("parse web-pattern.yaml error", e);
        }
    }
}
