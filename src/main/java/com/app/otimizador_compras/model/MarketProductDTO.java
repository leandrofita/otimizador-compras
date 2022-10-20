package com.app.otimizador_compras.model;


public class MarketProductDTO {

    private Long id;
    private Integer quantity;
    private Double unitPrice;
    private Long product;
    private Long market;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(final Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(final Long product) {
        this.product = product;
    }

    public Long getMarket() {
        return market;
    }

    public void setMarket(final Long market) {
        this.market = market;
    }

}
