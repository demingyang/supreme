package com.swagger;

import java.util.ArrayList;
import java.util.List;

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

/**
 * 
 * swagger配置文件
 * 
 * @author zhouxy
 *
 */
@EnableWebMvc // 如果没加这个会报错
@EnableSwagger2 // 启用Swagger2
public class RichgoSwaggerConfig {

	/**
	 * 
	 * 配置参数
	 * 
	 * @return
	 */
	@Bean
	public Docket buildDocket() {
		// 自定义参数
		List<Parameter> aParameters = new ArrayList<Parameter>();
		Parameter parameter = new ParameterBuilder().name("token").description("登录token")
				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		aParameters.add(parameter);

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInf()).globalOperationParameters(aParameters)
				.select().apis(RequestHandlerSelectors.basePackage("com.richgo.controller"))// controller路径
				.paths(PathSelectors.any()).build();
	}

	/**
	 * 
	 * 文档信息
	 * 
	 * @return
	 */
	private ApiInfo buildApiInf() {
		return new ApiInfoBuilder().title("理顾宝API").termsOfServiceUrl("").description("理顾宝app接口")
				.contact(new Contact("理顾宝", "", "zhouxingyu@chtwm.com")).build();

	}
}