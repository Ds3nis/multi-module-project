package ag.selm.manager.controller;

import ag.selm.manager.dto.UpdateProductDto;
import ag.selm.manager.entity.Product;
import ag.selm.manager.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@Data
@RequestMapping(("catalog/products/{productId:\\d+}"))
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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
    public String updateProduct(@ModelAttribute(value = "product", binding = false) Product product, @Validated UpdateProductDto updateProductDto,
                                BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("updateProductDto", updateProductDto);
            model.addAttribute("errors", bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).toList());
            return "catalog/products/edit";
        }else{
            productService.updateProduct(product.getId(), updateProductDto);
            return "redirect:/catalog/products/%d".formatted(product.getId());
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product){
        productService.deleteProduct(product.getId());
        return "redirect:/catalog/products/get";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(Model model, NoSuchElementException ex, HttpServletResponse response, Locale locale){
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("message", messageSource.getMessage(ex.getMessage(), null, locale));
        return "catalog/products/notfound";
    }

}
