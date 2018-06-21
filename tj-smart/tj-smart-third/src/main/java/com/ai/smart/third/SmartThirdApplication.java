package com.ai.smart.third;

import com.ai.smart.common.boot.AbstractApplication;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableSwagger2
@SpringBootApplication
@EntityScan(basePackageClasses = {SmartThirdApplication.class})
@EnableAutoConfiguration
@ComponentScan(basePackages ={"com.ai.smart"} )
@MapperScan(basePackages = {"com.ai.smart.third"})
@EnableTransactionManagement
public class SmartThirdApplication extends AbstractApplication{

	public static void main(String[] args) {
		start(args);
		System.setProperty("spring.config.name","applicationwx");
		SpringApplication.run(SmartThirdApplication.class, args);
	}

	public ApiInfo metadata() {
		return new ApiInfoBuilder().title("第三方接口api").description("接口url= host:port/third/${path}").version("1.0").build();
	}

}
