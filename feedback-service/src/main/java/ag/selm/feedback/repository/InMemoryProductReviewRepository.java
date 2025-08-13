//package ag.selm.feedback.repository;
//
//import ag.selm.feedback.entity.ProductReview;
//import org.springframework.stereotype.Repository;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.*;
//
//@Repository
//public class InMemoryProductReviewRepository implements ProductReviewRepository{
//
//    private final Map<Integer, List<ProductReview>> productReviews = new HashMap<>();
//
//    @Override
//    public Mono<ProductReview> save(ProductReview productReview) {
//        if (!productReviews.containsKey(productReview.getProductId())){
//            List<ProductReview> reviews = new ArrayList<>();
//            reviews.add(productReview);
//            productReviews.put(productReview.getProductId(), reviews);
//        }else {
//            productReviews.get(productReview.getProductId()).add(productReview);
//        }
//        return Mono.just(productReview);
//    }
//
//    @Override
//    public Flux<ProductReview> getReviewsByProductId(int productId) {
//        List<ProductReview> reviews = productReviews.get(productId);
//        return Flux.fromIterable(reviews != null ? reviews : Collections.emptyList());
//    }
//
//}
