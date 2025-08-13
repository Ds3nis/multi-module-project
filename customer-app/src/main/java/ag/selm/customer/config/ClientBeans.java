package ag.selm.customer.config;

import ag.selm.customer.client.ProductsWebClient;
import ag.selm.customer.client.WebClientFavoriteProductsClient;
import ag.selm.customer.client.WebClientProductReviewsClient;
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

    @Bean
    public WebClientProductReviewsClient webClientProductReviewsClient(
            @Value("${selmag.service.feedback.uri:http://localhost:8083}") String feedbackServiceBaseUrl
    ) {
        return new WebClientProductReviewsClient(WebClient.builder().baseUrl(feedbackServiceBaseUrl).build());
    }

    @Bean
    public WebClientFavoriteProductsClient webClientFavoriteProductsClient(
            @Value("${selmag.service.feedback.uri:http://localhost:8083}") String  feedbackServiceBaseUrl
    ) {
        return new WebClientFavoriteProductsClient(WebClient.builder().baseUrl(feedbackServiceBaseUrl).build());
    }
}
