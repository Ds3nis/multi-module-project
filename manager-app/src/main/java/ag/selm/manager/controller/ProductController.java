package ag.selm.manager.controller;

import ag.selm.manager.client.BadRequestException;
import ag.selm.manager.client.ProductsRestClient;
import ag.selm.manager.dto.UpdateProductDto;
import ag.selm.manager.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@Data
@RequestMapping(("catalog/products/{productId:\\d+}"))
@RequiredArgsConstructor
public class ProductController {

    private final ProductsRestClient productService;

    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") Integer productId) {
        return productService.findProduct(productId);
    }

    @GetMapping
    public String getProduct(){
        return "catalog/products/view";
    }

    @GetMapping("edit")
    public String editProduct(){
        return "catalog/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute(value = "product", binding = false) Product product, UpdateProductDto updateProductDto,
                                Model model){
        try {
            productService.updateProduct(product.id(), updateProductDto);
            return "redirect:/catalog/products/%d".formatted(product.id());
        }catch (BadRequestException ex){
            model.addAttribute("updateProductDto", updateProductDto);
            model.addAttribute("errors", ex.getErrors());
            return "catalog/products/edit";
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product){
        productService.deleteProduct(product.id());
        return "redirect:/catalog/products/get";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(Model model, NoSuchElementException ex, HttpServletResponse response, Locale locale){
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("message", messageSource.getMessage(ex.getMessage(), null, locale));
        return "catalog/products/notfound";
    }


}
