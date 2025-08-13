package ag.selm.customer.controller;

import ag.selm.customer.client.FavoriteProductsClient;
import ag.selm.customer.client.ProductReviewsClient;
import ag.selm.customer.client.ProductsClient;
import ag.selm.customer.dto.NewFavoriteProductDto;
import ag.selm.customer.dto.NewProductReviewDto;
import ag.selm.customer.entity.FavoriteProduct;
import ag.selm.customer.entity.Product;
import ag.selm.customer.entity.ProductReview;
import ag.selm.customer.exception.ClientBadRequestException;
import ag.selm.customer.exception.CustomNoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("catalog/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsClient productsClient;

    private final FavoriteProductsClient favoriteProductsService;

    private final ProductReviewsClient productReviewService;

    @ModelAttribute(value = "product", binding = false)
    public Mono<Product> product(@PathVariable("productId") Integer productId){
        return productsClient.getProduct(productId).switchIfEmpty(Mono.error(new CustomNoSuchElementException("customer.products.error.notfound", productId)));
    }

    @PostMapping("add-to-favorite")
    public Mono<String> addToFavorites(@ModelAttribute("product") Mono<Product> product, Model model){
        return product.map(Product::id).flatMap(favoriteProductId -> favoriteProductsService.addToFavorites(new NewFavoriteProductDto(favoriteProductId))
                .thenReturn("redirect:/catalog/products/%d".formatted(favoriteProductId))
                .onErrorResume(exception -> {
                            log.error(exception.getMessage(), exception);
                            return Mono.just("redirect:/catalog/products/%d".formatted(favoriteProductId));
                        }
                )

        );
    }

    @PostMapping("remove-from-favorite")
    public Mono<?> removeFromFavorites(@ModelAttribute("product") Mono<Product> product){
        return product.map(Product::id).flatMap(favoriteProductsService::removeFromFavorites)
                .thenReturn("redirect:/catalog/products/%d".formatted(product.block().id()));
    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") Integer productId, Model model) {
        Mono<Boolean> isFavoriteMono = favoriteProductsService.findFavoriteProductByProductId(productId)
                .hasElement(); // повертає Mono<Boolean>

        Mono<List<ProductReview>> reviewsMono = productReviewService.findProductReviewsByProductId(productId)
                .collectList();

        return Mono.zip(reviewsMono, isFavoriteMono)
                .doOnNext(tuple -> {
                    List<ProductReview> reviews = tuple.getT1();
                    Boolean isFavorite = tuple.getT2(); // Отримуємо Boolean значення

                    model.addAttribute("reviews", reviews);
                    model.addAttribute("isFavorite", isFavorite); // Додаємо в модель
                })
                .thenReturn("catalog/products/view");
    }




    @PostMapping("create-review")
    public Mono<String> createProductReview(@PathVariable("productId") Integer productId,
                                            NewProductReviewDto review,
                                            BindingResult bindingResult, Model model){
            return productReviewService.createProductReview(review)
                    .thenReturn("redirect:/catalog/products/%d".formatted(productId))
                    .onErrorResume(
                            ClientBadRequestException.class,
                            exception -> {
                                model.addAttribute("isFavorite", false);
                                model.addAttribute("newProductReviewDto", review);
                                model.addAttribute("errors", exception.getErrors());
                                return favoriteProductsService.findFavoriteProductByProductId(productId)
                                        .map(favoriteProduct -> {
                                            model.addAttribute("isFavorite", true);
                                            return "catalog/products/view";
                                        })
                                        .switchIfEmpty(Mono.fromCallable(() -> {
                                            model.addAttribute("isFavorite", false);
                                            return "catalog/products/view";
                                        }));
                            }
                    );
    }

    @ExceptionHandler(CustomNoSuchElementException.class)
    public String handleNoSuchElementException(CustomNoSuchElementException ex, Model model){
        model.addAttribute("error", ex);
        return "catalog/products/notfound";
    }
}
