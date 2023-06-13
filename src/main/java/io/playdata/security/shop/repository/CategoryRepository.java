package io.playdata.security.shop.repository;

import io.playdata.security.shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findBySlug(String categorySlug);
}