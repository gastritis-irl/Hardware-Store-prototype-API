package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.model.Category;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface CategoryService {

    Category findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void delete(Long id);

    Collection<Category> findAll();

    Collection<HardwarePart> findAllPartsByCategoryId(Long categoryId);

    Category findByName(String name);

    Page<Category> findAllWithPagination(Pageable pageable);
}
