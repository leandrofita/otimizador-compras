package com.app.otimizador_compras.service;

import com.app.otimizador_compras.domain.Category;
import com.app.otimizador_compras.domain.Product;
import com.app.otimizador_compras.model.CategoryDTO;
import com.app.otimizador_compras.repos.CategoryRepository;
import com.app.otimizador_compras.repos.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(final CategoryRepository categoryRepository,
            final ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll(Sort.by("id"))
                .stream()
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .collect(Collectors.toList());
    }

    public CategoryDTO get(final Long id) {
        return categoryRepository.findById(id)
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getId();
    }

    public void update(final Long id, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete(final Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setImage(category.getImage());
        categoryDTO.setProduct(category.getProduct() == null ? null : category.getProduct().getId());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setName(categoryDTO.getName());
        category.setImage(categoryDTO.getImage());
        final Product products = categoryDTO.getProduct() == null ? null : productRepository.findById(categoryDTO.getProduct())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Products not found"));
        category.setProduct(products);
        return category;
    }

}
