package com.app.otimizador_compras.model;

import java.util.List;


public class SlistDTO {

    private Long id;
    private Double total;
    private List<Long> clients;
    private List<Long> marketProducts;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(final Double total) {
        this.total = total;
    }

    public List<Long> getClients() {
        return clients;
    }

    public void setClients(final List<Long> clients) {
        this.clients = clients;
    }

	public List<Long> getMarketProducts() {
		return marketProducts;
	}

	public void setMarketProducts(List<Long> marketProducts) {
		this.marketProducts = marketProducts;
	}

}
