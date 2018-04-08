package com.richgo.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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
 * swagger
 *
 * @author sunyan
 * @create 2017-08-01 16:25
 **/
@EnableWebMvc
@EnableSwagger2
public class UserSwaggerConfig {
    public UserSwaggerConfig() {
    }

    @Bean
    public Docket buildDocket() {
        List<Parameter> aParameters = new ArrayList();
        Parameter parameter = (new ParameterBuilder()).name("token").description("登录token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        aParameters.add(parameter);
        return (new Docket(DocumentationType.SWAGGER_2)).apiInfo(this.buildApiInf()).globalOperationParameters(aParameters).select().apis(RequestHandlerSelectors.basePackage("com.user.controller")).paths(PathSelectors.any()).build();
    }

    private ApiInfo buildApiInf() {
        return (new ApiInfoBuilder()).title("员工管理API").termsOfServiceUrl("").description("员工管理接口").contact(new Contact("员工管理", "", "zhouxingyu@chtwm.com")).build();
    }
}
