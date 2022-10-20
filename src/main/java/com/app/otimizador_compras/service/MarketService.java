package com.app.otimizador_compras.service;

import com.app.otimizador_compras.domain.Adress;
import com.app.otimizador_compras.domain.Client;
import com.app.otimizador_compras.domain.Market;
import com.app.otimizador_compras.model.CompleteClientDTO;
import com.app.otimizador_compras.model.CompleteMarketDTO;
import com.app.otimizador_compras.model.MarketDTO;
import com.app.otimizador_compras.repos.AdressRepository;
import com.app.otimizador_compras.repos.MarketRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MarketService {

    private final MarketRepository marketRepository;
    
    private final AdressRepository adressRepository;

    public MarketService(final MarketRepository marketRepository, AdressRepository adressRepository) {
        this.marketRepository = marketRepository;
		this.adressRepository = adressRepository;
    }

    public List<MarketDTO> findAll() {
        return marketRepository.findAll(Sort.by("id"))
                .stream()
                .map(market -> mapToDTO(market, new MarketDTO()))
                .collect(Collectors.toList());
    }

    public MarketDTO get(final Long id) {
        return marketRepository.findById(id)
                .map(market -> mapToDTO(market, new MarketDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public CompleteMarketDTO create(final CompleteMarketDTO marketDTO) {
        final Market market = new Market();
        final Adress adress = new Adress();
        Long marketId;
        
        final Market existMarket = marketRepository.findByCnpj(marketDTO.getCnpj());
        //criando o Market
        if(existMarket == null) {
        	market.setName(marketDTO.getName());
        	market.setCnpj(marketDTO.getCnpj());
           marketId = marketRepository.save(market).getId();
        } else {
        	throw new DataIntegrityViolationException("This CNPJ is already registered");
        }
        
      //criando o adress
        adress.setStreet(marketDTO.getStreet());
        adress.setNumber(marketDTO.getNumber());
        adress.setDistrict(marketDTO.getDistrict());
        adress.setZipCode(marketDTO.getZipCode());
        Optional<Market> recoveredMarket = marketRepository.findById(marketId);
        if(recoveredMarket.isPresent()) {
        	adress.setMarket(recoveredMarket.get());
        } else {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Market not found");
        }
        Long adressId = adressRepository.save(adress).getId();
        
        Optional<Adress> recoverdAdress = adressRepository.findById(adressId);
        
        if(recoverdAdress.isPresent()) {
        	return new CompleteMarketDTO(recoveredMarket.get(), recoverdAdress.get());
        } else {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adress not found");
        }
    }

    public void update(final Long id, final MarketDTO marketDTO) {
        final Market market = marketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(marketDTO, market);
        marketRepository.save(market);
    }

    public void delete(final Long id) {
        marketRepository.deleteById(id);
    }

    private MarketDTO mapToDTO(final Market market, final MarketDTO marketDTO) {
        marketDTO.setId(market.getId());
        marketDTO.setName(market.getName());
        marketDTO.setAddress(market.getAddress().getId());
        marketDTO.setCnpj(market.getCnpj());
        return marketDTO;
    }

    private Market mapToEntity(final MarketDTO marketDTO, final Market market) {
        market.setName(marketDTO.getName());
        
        final Adress adress = marketDTO.getAddress() == null ? null : adressRepository.findById(marketDTO.getAddress())
        		.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adress not found"));
        market.setAddress(adress);
        market.setCnpj(marketDTO.getCnpj());
        return market;
    }

    public boolean cnpjExists(final String cnpj) {
        return marketRepository.existsByCnpjIgnoreCase(cnpj);
    }

}
