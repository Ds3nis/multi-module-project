package ag.selm.customer.controller;

import ag.selm.customer.client.ProductsClient;
import ag.selm.customer.dto.NewProductReviewDto;
import ag.selm.customer.entity.Product;
import ag.selm.customer.extension.CustomNoSuchElementException;
import ag.selm.customer.service.DefaultFavoriteProductsService;
import ag.selm.customer.service.FavoriteProductsService;
import ag.selm.customer.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("catalog/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsClient productsClient;

    private final FavoriteProductsService favoriteProductsService;

    private final ProductReviewService productReviewService;

    @ModelAttribute(value = "product", binding = false)
    public Mono<Product> product(@PathVariable("productId") Integer productId){
        return productsClient.getProduct(productId).switchIfEmpty(Mono.error(new CustomNoSuchElementException("customer.products.error.notfound", productId)));
    }

    @PostMapping("add-to-favorite")
    public Mono<String> addToFavorites(@ModelAttribute("product") Mono<Product> product){
        return product.map(Product::id).flatMap(favoriteProductId -> favoriteProductsService.addToFavorites(favoriteProductId)
                .thenReturn("redirect:/catalog/products/%d".formatted(favoriteProductId)));
    }

    @PostMapping("remove-from-favorite")
    public Mono<?> removeFromFavorites(@ModelAttribute("product") Mono<Product> product){
        return product.map(Product::id).flatMap(favoriteProductsService::removeFromFavorites)
                .thenReturn("redirect:/catalog/products/%d".formatted(product.block().id()));
    }

    @GetMapping()
    public Mono<String> getProductPage(@PathVariable("productId") Integer productId, Model model) {
        return Mono.fromCallable(() -> favoriteProductsService.isFavoriteProduct(productId))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(isFavorite -> model.addAttribute("isFavorite", isFavorite))
                .then(productReviewService.getReviewsByProductId(productId).collectList())
                .doOnNext(reviews -> model.addAttribute("reviews", reviews))
                .thenReturn("catalog/products/view");
    }


    @PostMapping("create-review")
    public Mono<String> createProductReview(@PathVariable("productId") Integer productId,
                                            @Validated NewProductReviewDto review,
                                            BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("isFavorite", false);
            model.addAttribute("newProductReviewDto", review);
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream().map(ObjectError::getDefaultMessage).toList());
            return Mono.fromCallable(() -> favoriteProductsService.isFavoriteProduct(productId))
                    .subscribeOn(Schedulers.boundedElastic())
                    .doOnNext(isFavorite -> model.addAttribute("isFavorite", isFavorite))
                    .thenReturn("catalog/products/view");
        }else{
            return productReviewService.createReview(productId, review.rating(), review.review())
                    .thenReturn("redirect:/catalog/products/%d".formatted(productId));
        }
    }

    @ExceptionHandler(CustomNoSuchElementException.class)
    public String handleNoSuchElementException(CustomNoSuchElementException ex, Model model){
        model.addAttribute("error", ex);
        return "catalog/products/notfound";
    }
}
