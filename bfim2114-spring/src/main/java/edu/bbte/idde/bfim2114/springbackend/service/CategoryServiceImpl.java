package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.model.Category;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.repository.CategoryRepository;
import edu.bbte.idde.bfim2114.springbackend.repository.HardwareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final HardwareRepository hardwareRepository;

    @Override
    @Cacheable(value = "Category", key = "#id")
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @CacheEvict(value = "Category", key = "#category.id", condition = "#category.id != null")
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @CacheEvict(value = "Category", key = "#id", condition = "#id != null")
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Collection<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override

    public Collection<HardwarePart> findAllPartsByCategoryId(Long categoryId) {
        return hardwareRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Page<Category> findAllWithPagination(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}
