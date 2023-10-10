package com.nsdl.electronic.store.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());
        ApiSelectorBuilder select=docket.select();
         docket.securityContexts(Arrays.asList(getSecurityContext()));
        docket.securitySchemes(Arrays.asList(getSchemes()));
        
       select.apis(RequestHandlerSelectors.any());
       select.paths(PathSelectors.any());
       Docket build=select.build();
	return build;
    }

    private ApiKey getSchemes() {
		
		return new ApiKey("Jwt","Authorization","header");
	}
   private SecurityContext getSecurityContext(){
	   SecurityContext context=SecurityContext
			   .builder()
			   .securityReferences(getsecurityReferences())
			   .build();
	   
	   
	   
    	return context;
    }

	private List<SecurityReference> getsecurityReferences() {
		AuthorizationScope[] scopes= {new AuthorizationScope("Global", "Access every thing")};
	return Arrays.asList(new SecurityReference("Jwt", scopes));
	
}

	private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfo(
            "Electronic Store Backend Project API",
            "This is a backend project created by Janardan Narvate",
            "1.0.0",
            "http://javadevelopment.com",
            new Contact(
                "Janardan",
                "https://instagram.com/janardan_123",
                "janardannarvate@gmail.com"
            ),
            "License of API",
            "https://javadevelopment.com/about",
            new ArrayList<>()
        );
        return apiInfo;
    }
}





