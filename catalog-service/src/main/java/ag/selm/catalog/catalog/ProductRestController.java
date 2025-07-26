package ag.selm.catalog.catalog;

import ag.selm.catalog.dto.ResponseProduct;
import ag.selm.catalog.dto.UpdateProductDto;
import ag.selm.catalog.entity.Product;
import ag.selm.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalog-api/products/{productId:\\d+}")
public class ProductRestController {

    private final ProductService productService;

    private final MessageSource messageSource;


    @ModelAttribute("responseProduct")
    public ResponseProduct responseProduct(@PathVariable("productId") int productId){
        return new ResponseProduct(productService.findProduct(productId));
    }

    @GetMapping
    public ResponseEntity<ResponseProduct> getProduct(@ModelAttribute("responseProduct") ResponseProduct responseProduct){
        return new ResponseEntity<>(responseProduct, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateProduct(@ModelAttribute(value = "responseProduct", binding = false) ResponseProduct responseProduct,
                                                         @Validated @RequestBody UpdateProductDto updateProductDto,
                                           BindingResult bindingResult) throws BindException{
        if (bindingResult.hasErrors()){
            if (bindingResult instanceof BindException exception){
                throw exception;
            }else {
                throw new BindException(bindingResult);
            }
        }else {
            productService.updateProduct(responseProduct.id(), updateProductDto);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<ResponseProduct> deleteProduct(@ModelAttribute("responseProduct") ResponseProduct responseProduct){
        productService.deleteProduct(responseProduct.id());
        return new ResponseEntity<>(responseProduct, HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException ex, Locale locale){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                messageSource.getMessage(ex.getMessage(), new Object[0], ex.getMessage() ,locale));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }


}
