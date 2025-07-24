package ag.selm.manager.controller;

import ag.selm.manager.dto.NewProductDto;
import ag.selm.manager.entity.Product;
import ag.selm.manager.repository.InMemoryProductRepository;
import ag.selm.manager.repository.ProductRepository;
import ag.selm.manager.service.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Data
@RequiredArgsConstructor
@RequestMapping("catalog/products")
public class ProductsController {

    private final ProductService productService;

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
    public String createProduct(@Validated NewProductDto newProductDto,  BindingResult bindingResult ,
                                Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("newProductDto", newProductDto);
            model.addAttribute("errors", bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).toList());
            return "catalog/products/create";
        }else{
            Product product = productService.createProduct(newProductDto);
            return  "redirect:/catalog/products/get";
        }
    }



  }
