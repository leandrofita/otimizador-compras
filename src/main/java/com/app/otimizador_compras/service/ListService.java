package com.app.otimizador_compras.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.otimizador_compras.domain.Client;
import com.app.otimizador_compras.domain.MarketProduct;
import com.app.otimizador_compras.domain.Slist;
import com.app.otimizador_compras.model.SlistDTO;
import com.app.otimizador_compras.repos.ClientRepository;
import com.app.otimizador_compras.repos.ListRepository;
import com.app.otimizador_compras.repos.MarketProductRepository;


@Transactional
@Service
public class ListService {

    private final ListRepository listRepository;
    private final ClientRepository clientRepository;
    private final MarketProductRepository marketProductRepository;

    public ListService(final ListRepository listRepository, final ClientRepository clientRepository,
            final MarketProductRepository marketProductRepository) {
        this.listRepository = listRepository;
        this.clientRepository = clientRepository;
        this.marketProductRepository = marketProductRepository;
    }

    public List<SlistDTO> findAll() {
        return listRepository.findAll(Sort.by("id"))
                .stream()
                .map(list -> mapToDTO(list, new SlistDTO()))
                .collect(Collectors.toList());
    }

    public SlistDTO get(final Long id) {
        return listRepository.findById(id)
                .map(list -> mapToDTO(list, new SlistDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final SlistDTO listDTO) {
        final com.app.otimizador_compras.domain.Slist list = new com.app.otimizador_compras.domain.Slist();
        mapToEntity(listDTO, list);
        return listRepository.save(list).getId();
    }

    public void update(final Long id, final SlistDTO listDTO) {
        final com.app.otimizador_compras.domain.Slist list = listRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(listDTO, list);
        listRepository.save(list);
    }

    public void delete(final Long id) {
        listRepository.deleteById(id);
    }

    private SlistDTO mapToDTO(final Slist list,
            final SlistDTO listDTO) {
        listDTO.setId(list.getId());
        listDTO.setTotal(list.getTotal());
        listDTO.setClients(list.getClients() == null ? null : list.getClients().stream()
                .map(Client::getId)
                .collect(Collectors.toList()));
        listDTO.setMarketProducts(list.getProducts() == null ? null : list.getProducts().stream()
        		.map(MarketProduct::getId)
        		.collect(Collectors.toList()));
        return listDTO;
    }

    private Slist mapToEntity(final SlistDTO listDTO,
            final Slist list) {
        list.setTotal(listDTO.getTotal());
        final List<Client> clients = clientRepository.findAllById(
                listDTO.getClients() == null ? Collections.emptyList() : listDTO.getClients());
        if (clients.size() != (listDTO.getClients() == null ? 0 : listDTO.getClients().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One of the clients was not found");
        }
        list.setClients(clients.stream().collect(Collectors.toSet()));
        final List<MarketProduct> products = marketProductRepository.findAllById(listDTO.getMarketProducts());
        if(products.size() != (listDTO.getMarketProducts() == null ? 0 : listDTO.getMarketProducts().size())) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One of the products was not found");
        }
        list.setProducts(products.stream().collect(Collectors.toSet()));
        return list;
    }

}
