package ag.selm.customer.controller;

import ag.selm.customer.client.ProductsClient;
import ag.selm.customer.entity.FavoriteProduct;
import ag.selm.customer.entity.Product;
import ag.selm.customer.service.FavoriteProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("catalog/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsClient productsClient;

    private final FavoriteProductsService favoriteProductsService;

    @GetMapping("list")
    public Mono<String> getAllProductsPage(Model model){
        return productsClient.getProducts()
                .collectList()
                .doOnNext(products -> model.addAttribute("products", products))
                .thenReturn("catalog/products/list");
    }

    @GetMapping("favorite-products")
    public Mono<String> getFavoriteProductsPage(Model model){
        return this.favoriteProductsService.findAllFavoriteProducts()
                .map(FavoriteProduct::getProductId)
                .collectList()
                .flatMap(favoriteProductsId -> productsClient.getProducts().filter(
                        product -> favoriteProductsId.contains(product.id())).collectList()
                        .doOnNext(products -> model.addAttribute("favoriteProducts", products)))
                .thenReturn("catalog/products/favorite-products");
    }
}
