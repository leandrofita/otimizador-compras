package com.app.otimizador_compras.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.app.otimizador_compras.domain.Adress;
import com.app.otimizador_compras.domain.Market;

public class CompleteMarketDTO {
	
		@Size(max = 255)
	    private String name;

	    @NotNull
	    @Size(max = 255)
	    private String cnpj;
	    
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

		public CompleteMarketDTO() {
			super();
		}

		public CompleteMarketDTO(Market market, Adress adress) {
			super();
			this.name = market.getName();
			this.cnpj = market.getCnpj();
			this.street = adress.getStreet();
			this.number = adress.getNumber();
			this.district = adress.getDistrict();
			this.city = adress.getCity();
			this.zipCode = adress.getZipCode();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCnpj() {
			return cnpj;
		}

		public void setCnpj(String cnpj) {
			this.cnpj = cnpj;
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
	    
	    

}
