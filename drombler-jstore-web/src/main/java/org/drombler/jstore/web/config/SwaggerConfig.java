package org.drombler.jstore.web.config;

import com.google.common.base.Predicates;
import org.drombler.jstore.web.controller.RestControllerUtils;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Controller
@EnableSwagger2
public class SwaggerConfig {

    public static final String OAUTH_NAME = "spring_oauth";
    @Autowired
    private BuildProperties buildProperties;

    @Autowired
    private KeycloakSpringBootProperties keycloakSpringBootProperties;

    @Bean
    public Docket jstoreApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(RestControllerUtils.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(createApiInfo())
                .groupName(RestControllerUtils.V1_PATH_SEGMENT)
//                .securitySchemes(buildSecurityScheme())
                .securitySchemes(Arrays.asList(securityScheme()))
//                .securityContexts(buildSecurityContext());
                .securityContexts(Arrays.asList(securityContext()));
    }

    private ApiInfo createApiInfo() {
        ApiInfoBuilder builder = new ApiInfoBuilder();
        return builder.title("JStore API")
                .version(buildProperties.getVersion())
                .contact(new Contact("Drombler GmbH", "https://www.drombler.com", "contact@drombler.com"))
                .build();
    }


    @Bean
    public SecurityConfiguration securityConfiguration() {

        Map<String, Object> additionalQueryStringParams=new HashMap<>();
        additionalQueryStringParams.put("nonce","123456");

        return SecurityConfigurationBuilder.builder()
                .realm(keycloakSpringBootProperties.getRealm())
                .clientId(keycloakSpringBootProperties.getResource())
                .clientSecret(getClientSecret())
                .scopeSeparator(" ")
                .appName("JStore")
                .additionalQueryStringParams(additionalQueryStringParams)
                .build();
    }

    private String getClientSecret() {
        return (String) keycloakSpringBootProperties.getCredentials().get("secret");
    }

//
//    private List<SecurityContext> buildSecurityContext() {
//        List<SecurityReference> securityReferences = new ArrayList<>();
//
//        SecurityReference securityReference = SecurityReference.builder()
//                .reference("oauth2")
//                .scopes(scopes().toArray(new AuthorizationScope[]{}))
//                .build();
//        securityReferences.add(securityReference);
//
//        SecurityContext context = SecurityContext.builder()
//                .forPaths(Predicates.alwaysTrue())
//                .securityReferences(securityReferences)
//                .build();
//
//        List<SecurityContext> ret = new ArrayList<>();
//        ret.add(context);
//        return ret;
//    }

//    private List<? extends SecurityScheme> buildSecurityScheme() {
//        List<SecurityScheme> lst = new ArrayList<>();
//        // lst.add(new ApiKey("api_key", "X-API-KEY", "header"));
//
//        LoginEndpoint login = new LoginEndpointBuilder()
////                .url(keycloakSpringBootProperties.getAuthServerUrl())
//                .url("http://127.0.0.1:8070/auth/realms/drombler-staging/protocol/openid-connect/auth")
//                .build();
//
//        List<GrantType> gTypes = new ArrayList<>();
//        gTypes.add(new ImplicitGrant(login, "access_token"));
////        gTypes.add(new ImplicitGrant(login, "acces_token"));
//
//        lst.add(new OAuth("oauth2", scopes(), gTypes));
//        return lst;
//    }

//    private List<AuthorizationScope> scopes() {
//        List<AuthorizationScope> scopes = new ArrayList<>();
//        for (String scopeItem : new String[]{"openid=openid", "profile=profile"}) {
//            String scope[] = scopeItem.split("=");
//            if (scope.length == 2) {
//                scopes.add(new AuthorizationScopeBuilder().scope(scope[0]).description(scope[1]).build());
//            }
////            else {
////                log.warn("Scope '{}' is not valid (format is scope=description)", scopeItem);
////            }
//        }
//
//        return scopes;
//    }

    private SecurityScheme securityScheme() {
        GrantType grantType =
                new AuthorizationCodeGrantBuilder()
                        .tokenEndpoint(new TokenEndpoint(keycloakSpringBootProperties.getAuthServerUrl() + "/realms/" + keycloakSpringBootProperties.getRealm() + "/protocol/openid-connect/token", RestControllerUtils.V1_PATH_SEGMENT))
                        .tokenRequestEndpoint(
                                new TokenRequestEndpoint(keycloakSpringBootProperties.getAuthServerUrl()  + "/realms/" + keycloakSpringBootProperties.getRealm() + "/protocol/openid-connect/auth", keycloakSpringBootProperties.getResource(), getClientSecret()))
                        .build();

        SecurityScheme oauth =
                new OAuthBuilder()
                        .name(OAUTH_NAME)
                        .grantTypes(Arrays.asList(grantType))
                        .scopes(Arrays.asList(scopes()))
                        .build();
        return oauth;
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("user", "for CRUD operations"),
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations")
        };
        return scopes;
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference(OAUTH_NAME, scopes())))
//                .forPaths(PathSelectors.regex(ALLOWED_PATHS))
                .forPaths(Predicates.alwaysTrue())
                .build();
    }
}
