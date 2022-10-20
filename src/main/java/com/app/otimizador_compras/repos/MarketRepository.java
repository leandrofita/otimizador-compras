package com.app.otimizador_compras.repos;

import com.app.otimizador_compras.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MarketRepository extends JpaRepository<Market, Long> {

    boolean existsByCnpjIgnoreCase(String cnpj);

	Market findByCnpj(String cnpj);

}
