package ag.selm.feedback.service;

import ag.selm.feedback.entity.Product;
import ag.selm.feedback.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewService {

    Mono<ProductReview> createReview(Integer productId, Integer rating, String review);

    Flux<ProductReview> getReviewsByProductId(Integer productId);
}
