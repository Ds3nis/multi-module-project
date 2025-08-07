package ag.selm.customer.service;

import ag.selm.customer.entity.Product;
import ag.selm.customer.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewService {

    Mono<ProductReview> createReview(Integer productId, Integer rating, String review);

    Flux<ProductReview> getReviewsByProductId(Integer productId);
}
