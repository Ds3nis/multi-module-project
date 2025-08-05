package ag.selm.catalog.catalog;

import ag.selm.catalog.dto.NewProductDto;
import ag.selm.catalog.dto.ResponseProduct;
import ag.selm.catalog.entity.Product;
import ag.selm.catalog.repository.ProductRepository;
import ag.selm.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalog-api/products")
public class ProductsRestController {

    private final ProductService productService;


    @GetMapping("get")
    public Iterable<Product> getProducts(){
        return productService.findAllProducts();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@Validated @RequestBody NewProductDto newProductDto,
                                           BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder
    ) throws BindException{
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception){
                throw exception;
            }else {
                throw new BindException(bindingResult);
            }
        }else {
            Product product = productService.createProduct(newProductDto);
            ResponseProduct responseProduct = new ResponseProduct(product.getId(),
                    product.getTitle(), product.getDetails());
            return ResponseEntity.created(uriComponentsBuilder.pathSegment("catalog-api/products/{productId}").
                    buildAndExpand(product.getId()).toUri()).body(responseProduct);
        }
    }
}
