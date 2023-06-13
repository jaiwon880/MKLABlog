package io.playdata.security.shop.controller;

import io.playdata.security.shop.model.Product;
import io.playdata.security.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }



    @GetMapping("/category/{categorySlug}")
    public String showProductsInCategory(@PathVariable String categorySlug, Model model) {
        List<Product> products = productService.getProductsByCategory(categorySlug);
        model.addAttribute("products", products);
        return "shop/list";
    }

    // ProductController.java
    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProductDetail(id);
        model.addAttribute("product", product);
        return "shop/detail";
    }
}
