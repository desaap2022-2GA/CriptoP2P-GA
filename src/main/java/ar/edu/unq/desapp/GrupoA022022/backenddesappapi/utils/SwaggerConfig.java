package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()/*
                .apis(RequestHandlerSelectors.basePackage("ar.edu.unq.desapp.GrupoA022022.backenddesappapi.webservice"))
              */.paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CRYPTOP2P API")
                .description("CRYPTOP2P API reference for developers")
                .version("1.0")
                .build();
    }
}
