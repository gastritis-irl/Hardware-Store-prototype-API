package edu.bbte.idde.bfim2114.springbackend.controller;


import edu.bbte.idde.bfim2114.springbackend.dto.CategoryPageDTO;
import edu.bbte.idde.bfim2114.springbackend.mapper.CategoryMapper;
import edu.bbte.idde.bfim2114.springbackend.model.Category;
import edu.bbte.idde.bfim2114.springbackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        log.info("GET: /api/category/{}", id);
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CategoryPageDTO> getAllCategoriesWithPagination(
        @RequestParam("pageNumber") Optional<Integer> pageNumber,
        @RequestParam("pageSize") Optional<Integer> pageSize,
        @RequestParam("sortBy") Optional<String> sortBy,
        @RequestParam("direction") Optional<String> direction
    ) {
        log.info("GET: /api/category");
        int page = pageNumber.orElse(1) - 1;
        int size = pageSize.orElse(14);
        String sort = sortBy.orElse("id");
        Sort.Direction dir = "ASC".equalsIgnoreCase(direction.orElse("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort));
        Page<Category> categories = categoryService.findAllWithPagination(pageable);
        CategoryPageDTO categoryPageDTO = categoryMapper.toCategoryPageDTO(categories.getContent(),
            categories.getTotalPages(), categories.getTotalElements());

        return new ResponseEntity<>(categoryPageDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        log.info("DELETE: /api/category/{}", id);
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {
        log.info("POST: /api/category");
        Category res = categoryService.save(category);
        if (res == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        log.info("PUT: /api/category/{}", id);

        Category oldCategory = categoryService.findById(id);
        if (oldCategory == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        oldCategory.setName(category.getName());
        oldCategory.setDescription(category.getDescription());
        Category res = categoryService.update(oldCategory);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
