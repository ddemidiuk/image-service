package org.ddemidiuk.example.images.configuration;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static springfox.documentation.schema.AlternateTypeRules.newRule;


/**
 * Configures Swagger.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Autowired
    private volatile TypeResolver typeResolver;

    /**
     * Configure Swagger.
     *
     * @return bean that configures Swagger.
     */
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(new ApiInfo(
                "Image Service",
                "Image Service",
                "v1",
                null,
                new Contact(null, null, null),
                null,
                null,
                Collections.emptyList()
            ))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, Date.class)
                .directModelSubstitute(ZonedDateTime.class, Date.class)
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(
                                typeResolver.resolve(DeferredResult.class,
                                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)),
                        newRule(
                                typeResolver.resolve(Optional.class,
                                        typeResolver.resolve(WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)
                        ))
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(false);
    }


    /**
     * Configure UI.
     *
     * @return bean that configures UI.
     */
    @Bean
    public UiConfiguration uiConfig() {
        // No validator
        UiConfigurationBuilder builder = UiConfigurationBuilder.builder();
        builder.docExpansion(DocExpansion.NONE);
        builder.operationsSorter(OperationsSorter.ALPHA);
        builder.defaultModelRendering(ModelRendering.EXAMPLE);
        return builder.build();
    }
}
