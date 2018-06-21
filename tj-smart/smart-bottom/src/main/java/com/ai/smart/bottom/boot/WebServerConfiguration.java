package com.ai.smart.bottom.boot;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * tomcat配置
 */
@Configuration
public class WebServerConfiguration {

    @Value("${web.port}")
    private int ports;

    @Bean
    public EmbeddedServletContainerFactory createEmbeddedServletContainerFactory(){
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        tomcatFactory.setPort(ports);
        tomcatFactory.setBaseDirectory(new File("/cqwt/logistics/tomcat"));
        tomcatFactory.setContextPath("/bottom");
        tomcatFactory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
        return tomcatFactory;
    }

    class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer{
        public void customize(Connector connector){
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            protocol.setAcceptCount(2000);
            //设置最大连接数
            protocol.setMaxConnections(2000);
            //设置最大线程数
            protocol.setMaxThreads(2000);
            protocol.setConnectionTimeout(30000);
        }
    }
}
