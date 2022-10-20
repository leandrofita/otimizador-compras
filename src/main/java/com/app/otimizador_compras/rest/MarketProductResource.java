package com.app.otimizador_compras.rest;

import com.app.otimizador_compras.model.MarketProductDTO;
import com.app.otimizador_compras.service.MarketProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/marketProducts", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarketProductResource {

    private final MarketProductService marketProductService;

    public MarketProductResource(final MarketProductService marketProductService) {
        this.marketProductService = marketProductService;
    }

    @GetMapping
    public ResponseEntity<List<MarketProductDTO>> getAllMarketProducts() {
        return ResponseEntity.ok(marketProductService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarketProductDTO> getMarketProduct(@PathVariable final Long id) {
        return ResponseEntity.ok(marketProductService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMarketProduct(
            @RequestBody @Valid final MarketProductDTO marketProductDTO) {
        return new ResponseEntity<>(marketProductService.create(marketProductDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMarketProduct(@PathVariable final Long id,
            @RequestBody @Valid final MarketProductDTO marketProductDTO) {
        marketProductService.update(id, marketProductDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMarketProduct(@PathVariable final Long id) {
        marketProductService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
