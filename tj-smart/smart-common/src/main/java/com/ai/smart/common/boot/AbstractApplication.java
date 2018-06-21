package com.ai.smart.common.boot;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.ai.smart.common.enums.Env;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public abstract class AbstractApplication {
	
	// 环境参数前缀
	private static final String ENV_EX = "--spring.profiles.active=";
	// 环境参数属性名
	private static final String ENV_PROPERTY_NAME ="user.env";
	
	@Value("${spring.application.name}")
	private String applicationName;
	

	public static void start(String[] args) {
		String env = Env.LOCAL.name();
		if (args != null && args.length > 0) {
			for (String arg : args) {
				if (null != arg && arg.startsWith(ENV_EX)) {
					env = arg.substring(ENV_EX.length());
				}
			}
		}
		System.out.println(" *********************************");
		System.out.println("*** 				***");
		System.out.println("*** server is starting,env is	***");
		System.out.println("*** 				***");
		System.out.println("***		" + env.toUpperCase() + " !		***");
		System.out.println("*** 				***");
		System.out.println(" *********************************");
		
		System.setProperty(ENV_PROPERTY_NAME, env.toLowerCase());
		System.setProperty("sun.net.client.defaultConnectTimeout", "2000");
		System.setProperty("sun.net.client.defaultReadTimeout", "1500");
		System.setProperty("user.timezone", "GMT+8");
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		
	}

	@Bean
	public Docket api() {
//		List<Parameter> pars = new ArrayList<Parameter>();
//		ParameterBuilder tokenPar = new ParameterBuilder();
//		tokenPar.name("Authorization").description("mork").name("X-Mork-Id").defaultValue("1233456").description("令牌")
//				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//		pars.add(tokenPar.build());
//		ParameterBuilder Authorization = new ParameterBuilder();
//		Authorization.name("Authorization").description("用户token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//		pars.add(Authorization.build());
		if("prod".equals(System.getProperty("user.env"))){
			return new Docket(DocumentationType.SWAGGER_2)//.ignoredParameterTypes(CurrentlyLoggedUser.class)
					.groupName("smart").apiInfo(metadata()).select().paths(PathSelectors.none())
					.apis(RequestHandlerSelectors.basePackage("com.ai.smart")).build();

		}else{
			return new Docket(DocumentationType.SWAGGER_2)//.ignoredParameterTypes(CurrentlyLoggedUser.class)
					.groupName("smart").apiInfo(metadata()).select().paths(regex("/*.*"))
					.apis(RequestHandlerSelectors.basePackage("com.ai.smart")).build();//.globalOperationParameters(pars);
		}
	}

	public abstract ApiInfo metadata();
}
