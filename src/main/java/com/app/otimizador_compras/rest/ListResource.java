package com.app.otimizador_compras.rest;

import com.app.otimizador_compras.model.SlistDTO;
import com.app.otimizador_compras.service.ListService;
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
@RequestMapping(value = "/api/lists", produces = MediaType.APPLICATION_JSON_VALUE)
public class ListResource {

    private final ListService listService;

    public ListResource(final ListService listService) {
        this.listService = listService;
    }

    @GetMapping
    public ResponseEntity<List<SlistDTO>> getAllLists() {
        return ResponseEntity.ok(listService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlistDTO> getList(@PathVariable final Long id) {
        return ResponseEntity.ok(listService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createList(@RequestBody @Valid final SlistDTO listDTO) {
        return new ResponseEntity<>(listService.create(listDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateList(@PathVariable final Long id,
            @RequestBody @Valid final SlistDTO listDTO) {
        listService.update(id, listDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteList(@PathVariable final Long id) {
        listService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
