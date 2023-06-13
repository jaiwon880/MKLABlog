package io.playdata.security.shop.service;

import io.playdata.security.shop.model.Category;
import io.playdata.security.shop.model.Product;
import io.playdata.security.shop.repository.CategoryRepository;
import io.playdata.security.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ShopService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategoryId(Long id) {
        return productRepository.findByCategoryId(id);
    }
}
