package com.app.otimizador_compras.repos;

import com.app.otimizador_compras.domain.Slist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ListRepository extends JpaRepository<Slist, Long> {
}
