package ag.selm.manager.controller;

import ag.selm.manager.client.BadRequestException;
import ag.selm.manager.client.ProductsRestClient;
import ag.selm.manager.dto.NewProductDto;
import ag.selm.manager.entity.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Data
@RequiredArgsConstructor
@RequestMapping("catalog/products")
public class ProductsController {

    private final ProductsRestClient productService;

    @GetMapping("get")
    public String getProducts(Model model){
        model.addAttribute("products", productService.findAllProducts());
        return "catalog/products/list";
    }

    @GetMapping("create")
    public String createProduct(){
        return "catalog/products/create";
    }

    @PostMapping("create")
    public String createProduct(NewProductDto newProductDto, Model model){
        try {
            productService.createProduct(newProductDto);
            return  "redirect:/catalog/products/get";
        }catch (BadRequestException ex){
            model.addAttribute("newProductDto", newProductDto);
            model.addAttribute("errors", ex.getErrors());
            return "catalog/products/create";
        }
    }



  }
