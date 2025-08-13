package ag.selm.feedback.controller;

import ag.selm.feedback.dto.NewProductReviewDto;
import ag.selm.feedback.entity.ProductReview;
import ag.selm.feedback.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("feedback-api/product-reviews")
@RequiredArgsConstructor
public class ProductReviewsRestController {

    private final ProductReviewService productReviewService;

    @GetMapping("/{productId:\\d+}")
    public Flux<ProductReview> findProductReviewsByProductId(@PathVariable("productId") Integer productId){
        return this.productReviewService.getReviewsByProductId(productId);
    }

    @PostMapping
    public Mono<ResponseEntity<ProductReview>> createProductReview(
            @Validated @RequestBody Mono<NewProductReviewDto> newProductReviewDto, UriComponentsBuilder uriComponentsBuilder){
        return newProductReviewDto.flatMap(newProductReview ->
                this.productReviewService.createReview(newProductReview.productId(), newProductReview.rating(),
                        newProductReview.review())
                        .map(productReview -> ResponseEntity
                                .created(uriComponentsBuilder.replacePath("feedback-api/product-reviews/{id}").build(productReview.getUuid()))
                                .body(productReview)));
    }


}
