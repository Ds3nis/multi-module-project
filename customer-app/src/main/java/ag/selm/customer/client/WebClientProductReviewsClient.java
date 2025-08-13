package ag.selm.customer.client;

import ag.selm.customer.dto.NewProductReviewDto;
import ag.selm.customer.entity.ProductReview;
import ag.selm.customer.exception.ClientBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class WebClientProductReviewsClient implements ProductReviewsClient{

    private final WebClient webClient;


    @Override
    public Flux<ProductReview> findProductReviewsByProductId(int productId) {
        return webClient
                .get()
                .uri("/feedback-api/product-reviews/{productId}", productId)
                .retrieve()
                .bodyToFlux(ProductReview.class);
    }

    @Override
    public Mono<ProductReview> createProductReview(NewProductReviewDto newProductReviewDto) {
        return webClient
                .post()
                .uri("feedback-api/product-reviews")
                .bodyValue(newProductReviewDto)
                .retrieve()
                .bodyToMono(ProductReview.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(
                                exception,
                                (List<String>) exception.getResponseBodyAs(ProblemDetail.class).getProperties().get("errors")));
    }
    }
