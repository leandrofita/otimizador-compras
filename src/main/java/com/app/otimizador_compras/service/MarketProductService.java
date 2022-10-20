package com.app.otimizador_compras.service;

import com.app.otimizador_compras.domain.Market;
import com.app.otimizador_compras.domain.MarketProduct;
import com.app.otimizador_compras.domain.Product;
import com.app.otimizador_compras.model.MarketProductDTO;
import com.app.otimizador_compras.repos.MarketProductRepository;
import com.app.otimizador_compras.repos.MarketRepository;
import com.app.otimizador_compras.repos.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MarketProductService {

    private final MarketProductRepository marketProductRepository;
    private final ProductRepository productRepository;
    private final MarketRepository marketRepository;

    public MarketProductService(final MarketProductRepository marketProductRepository,
            final ProductRepository productRepository, final MarketRepository marketRepository) {
        this.marketProductRepository = marketProductRepository;
        this.productRepository = productRepository;
        this.marketRepository = marketRepository;
    }

    public List<MarketProductDTO> findAll() {
        return marketProductRepository.findAll(Sort.by("id"))
                .stream()
                .map(marketProduct -> mapToDTO(marketProduct, new MarketProductDTO()))
                .collect(Collectors.toList());
    }

    public MarketProductDTO get(final Long id) {
        return marketProductRepository.findById(id)
                .map(marketProduct -> mapToDTO(marketProduct, new MarketProductDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final MarketProductDTO marketProductDTO) {
        final MarketProduct marketProduct = new MarketProduct();
        mapToEntity(marketProductDTO, marketProduct);
        return marketProductRepository.save(marketProduct).getId();
    }

    public void update(final Long id, final MarketProductDTO marketProductDTO) {
        final MarketProduct marketProduct = marketProductRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(marketProductDTO, marketProduct);
        marketProductRepository.save(marketProduct);
    }

    public void delete(final Long id) {
        marketProductRepository.deleteById(id);
    }

    private MarketProductDTO mapToDTO(final MarketProduct marketProduct,
            final MarketProductDTO marketProductDTO) {
        marketProductDTO.setId(marketProduct.getId());
        marketProductDTO.setQuantity(marketProduct.getQuantity());
        marketProductDTO.setUnitPrice(marketProduct.getUnitPrice());
        marketProductDTO.setProduct(marketProduct.getProduct() == null ? null : marketProduct.getProduct().getId());
        marketProductDTO.setMarket(marketProduct.getMarket() == null ? null : marketProduct.getMarket().getId());
        return marketProductDTO;
    }

    private MarketProduct mapToEntity(final MarketProductDTO marketProductDTO,
            final MarketProduct marketProduct) {
        marketProduct.setQuantity(marketProductDTO.getQuantity());
        marketProduct.setUnitPrice(marketProductDTO.getUnitPrice());
        final Product product = marketProductDTO.getProduct() == null ? null : productRepository.findById(marketProductDTO.getProduct())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        marketProduct.setProduct(product);
        final Market market = marketProductDTO.getMarket() == null ? null : marketRepository.findById(marketProductDTO.getMarket())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "market not found"));
        marketProduct.setMarket(market);
        return marketProduct;
    }

}
