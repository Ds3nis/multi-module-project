package ag.selm.manager.config;

import ag.selm.manager.client.RestClientProductsRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductsRestClient restClientProductsRestClient(
            @Value("${selmag.service.catalog.uri:http://localhost:8080}") String catalogServiceBaseUrl){
        return new RestClientProductsRestClient(RestClient.builder().baseUrl(catalogServiceBaseUrl).build());
    }
}
