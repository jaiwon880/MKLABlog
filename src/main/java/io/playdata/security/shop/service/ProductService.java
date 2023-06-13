package io.playdata.security.shop.service;

import io.playdata.security.shop.exception.ResourceNotFoundException;
import io.playdata.security.shop.model.Category;
import io.playdata.security.shop.model.Product;
import io.playdata.security.shop.repository.CategoryRepository;
import io.playdata.security.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String categorySlug) {
        Category category = categoryRepository.findBySlug(categorySlug);
        return productRepository.findByCategory(category);
    }
    public Product getProductDetail(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }


}