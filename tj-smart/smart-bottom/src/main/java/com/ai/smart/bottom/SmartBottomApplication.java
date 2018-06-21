package com.ai.smart.bottom;

import com.ai.smart.common.boot.AbstractApplication;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@EnableSwagger2
@SpringBootApplication
@EntityScan(basePackageClasses = {SmartBottomApplication.class})
@EnableAutoConfiguration
@ComponentScan(basePackages ={"com.ai.smart"} )
@MapperScan(basePackages = {"com.ai.smart.bottom"})
@EnableTransactionManagement
public class SmartBottomApplication extends AbstractApplication{

	public static void main(String[] args) {
		start(args);
		SpringApplication.run(SmartBottomApplication.class, args);
	}


	public ApiInfo metadata() {
		return new ApiInfoBuilder().title("重庆移动不限量小程序接口api").description("接口url= host:port/bottom/${path}")
				.contact(new Contact("daizy","","daizy@asiainfo.com")).version("1.0").build();
	}

}
