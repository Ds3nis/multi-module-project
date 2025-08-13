package ag.selm.feedback.controller;

import ag.selm.feedback.entity.FavoriteProduct;
import ag.selm.feedback.service.FavoriteProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("feedback-api/favorite-products")
@RequiredArgsConstructor
public class FavoriteProductsRestController {

    private final FavoriteProductsService favoriteProductsService;

    @GetMapping
    public Flux<FavoriteProduct> getFavoriteProducts(){
        return this.favoriteProductsService.findAllFavoriteProducts();
    }

    @GetMapping("by-productId/{productId:\\d+}")
    public Mono<FavoriteProduct> findFavoriteProductByProductId(@PathVariable("productId") Integer productId){
        return this.favoriteProductsService.findFavoriteProductByProductId(productId);
    }

    @PostMapping
    public Mono<ResponseEntity<FavoriteProduct>> createFavoriteProduct(
            @Validated @RequestBody Mono<FavoriteProduct> favoriteProduct, UriComponentsBuilder uriComponentsBuilder){
        return favoriteProduct.flatMap(favoriteProduct1 -> this.favoriteProductsService.addToFavorites(favoriteProduct1)
                .map(product -> ResponseEntity.created(uriComponentsBuilder.replacePath("feedback-api/favorite-products/{id}")
                                .build(favoriteProduct1.getProductId()))
                        .body(product)));
    }

    @DeleteMapping("by-productId/{productId:\\d+}")
    public Mono<ResponseEntity<Void>> deleteFavoriteProduct(@PathVariable("productId") Integer productId){
        return this.favoriteProductsService.removeFromFavorites(productId).then(Mono.just(
                ResponseEntity.noContent().build()
        ));
    }



}
