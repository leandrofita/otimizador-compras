package com.app.otimizador_compras.repos;

import com.app.otimizador_compras.domain.MarketProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MarketProductRepository extends JpaRepository<MarketProduct, Long> {
}
