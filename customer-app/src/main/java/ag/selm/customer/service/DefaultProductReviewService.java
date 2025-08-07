package ag.selm.customer.service;

import ag.selm.customer.entity.ProductReview;
import ag.selm.customer.repository.ProductReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DefaultProductReviewService implements ProductReviewService{

    private final ProductReviewRepository productReviewRepository;

    @Override
    public Mono<ProductReview> createReview(Integer productId, Integer rating, String review) {
        return this.productReviewRepository.save(new ProductReview(UUID.randomUUID(), productId, rating, review));
    }

    @Override
    public Flux<ProductReview> getReviewsByProductId(Integer productId) {
        return this.productReviewRepository.getReviewsByProductId(productId);
    }
}
