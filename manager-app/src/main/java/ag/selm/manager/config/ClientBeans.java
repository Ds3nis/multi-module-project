package ag.selm.manager.config;

import ag.selm.manager.client.RestClientProductsRestClient;
import ag.selm.manager.security.OAuthClientRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductsRestClient restClientProductsRestClient(
            @Value("${selmag.service.catalog.uri:http://localhost:8080}") String catalogServiceBaseUrl,
            @Value("${selmag.service.catalog.username:catalog_service_user}") String catalogUsername,
            @Value("${selmag.service.catalog.password:password}") String catalogPassword,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${selmag.service.catalog.registration-id:keycloak}") String registrationId
    ){
        return new RestClientProductsRestClient(RestClient.builder()
                .baseUrl(catalogServiceBaseUrl)
                .requestInterceptor(new OAuthClientRequestInterceptor(
                        new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository),
                        registrationId))
//                .requestInterceptor(new BasicAuthenticationInterceptor("catalog_service_user", "password"))
                .build());
//        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString(
//                (catalogUsername + ":" + catalogPassword).getBytes()
//        );
//
//        RestClient restClient = RestClient.builder()
//                .baseUrl(catalogServiceBaseUrl)
//                .defaultHeader("Authorization", basicAuthHeader)
//                .build();
//
//        return new RestClientProductsRestClient(restClient);
    }
}
