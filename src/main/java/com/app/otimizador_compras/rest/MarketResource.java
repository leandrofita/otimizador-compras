package com.app.otimizador_compras.rest;

import com.app.otimizador_compras.model.CompleteMarketDTO;
import com.app.otimizador_compras.model.MarketDTO;
import com.app.otimizador_compras.service.MarketService;
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
@RequestMapping(value = "/api/markets", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarketResource {

    private final MarketService marketService;

    public MarketResource(final MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping
    public ResponseEntity<List<MarketDTO>> getAllMarkets() {
        return ResponseEntity.ok(marketService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarketDTO> getMarket(@PathVariable final Long id) {
        return ResponseEntity.ok(marketService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<CompleteMarketDTO> createMarket(@RequestBody @Valid final CompleteMarketDTO marketDTO) {
        return new ResponseEntity<>(marketService.create(marketDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMarket(@PathVariable final Long id,
            @RequestBody @Valid final MarketDTO marketDTO) {
        marketService.update(id, marketDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMarket(@PathVariable final Long id) {
        marketService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
