package ag.selm.customer.config;

import ag.selm.customer.client.ProductsWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientBeans {

    @Bean
    public ProductsWebClient productsWebClient(
            @Value("${selmag.service.catalog.uri:http://localhost:8080}") String catalogServiceBaseUrl
    ) {
        return new ProductsWebClient(WebClient.builder().baseUrl(catalogServiceBaseUrl).build());
    }
}
