package com.app.otimizador_compras.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class AdressDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String street;
    
    @NotNull
    @Size(max = 255)
    private Long number;
    
    @NotNull
    @Size(max = 255)
    private String district;
    
    @NotNull
    @Size(max = 255)
    private String city;
    
    @Size(max = 255)
    private String zipCode;
    
    private Long client;
    
    private Long market;
    
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getClient() {
		return client;
	}

	public void setClient(Long client) {
		this.client = client;
	}

	public Long getMarket() {
		return market;
	}

	public void setMarket(Long market) {
		this.market = market;
	}   

}
