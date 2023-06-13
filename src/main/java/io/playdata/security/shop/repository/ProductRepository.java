package io.playdata.security.shop.repository;

import io.playdata.security.shop.model.Category;
import io.playdata.security.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}