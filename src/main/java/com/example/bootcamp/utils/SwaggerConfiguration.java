package com.example.bootcamp.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
	
	@Bean
    public Docket userContactApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 
                .apis(RequestHandlerSelectors.any())
                .build().apiInfo(metaData())
        .globalOperationParameters(Arrays.asList(new ParameterBuilder()
        		.name(Constant.AUTHORIZATION_HEADER)
        		.modelRef(new ModelRef("string"))
        		.parameterType("header")
        		.required(true).build()));
                
    }

	private ApiInfo metaData() {
        return  new ApiInfo(
                "User-Contact API",
                "User-Contact REST API Framework",
                "1.0",
                "Terms of service",
                new Contact("User-Contact", null,null),
               "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
    }

}

