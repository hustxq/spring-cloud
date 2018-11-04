package com.sse.ftp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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


/**
 * Created by qxiong on 2018/6/4.
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket creatRestApi() {
        List<Parameter> parameters = new ArrayList<Parameter>();
        ParameterBuilder username = new ParameterBuilder();
        username.name("username")
                .description("用户名")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true);
        parameters.add(username.build());

        ParameterBuilder role = new ParameterBuilder();
        role.name("role")
                .description("角色名")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true);
        parameters.add(role.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sse.ftp.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Thanos Ftp Cleaner RESTful API")
                .contact(new Contact("qxiong", "", "qxiong@sse.com.cn"))
                .version("1.0")
                .description("API4FtpCleaner")
                .build();
    }
}
