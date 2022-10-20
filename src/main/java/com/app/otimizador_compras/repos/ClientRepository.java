package com.app.otimizador_compras.repos;

import com.app.otimizador_compras.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmailIgnoreCase(String email);

	Client findByEmail(String email);

}
