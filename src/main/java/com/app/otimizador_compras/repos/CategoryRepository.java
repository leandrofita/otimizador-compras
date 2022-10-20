package com.app.otimizador_compras.repos;

import com.app.otimizador_compras.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
