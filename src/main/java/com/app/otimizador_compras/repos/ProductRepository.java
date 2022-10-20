package com.app.otimizador_compras.repos;

import com.app.otimizador_compras.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
