package edu.bbte.idde.bfim2114.springbackend.repository;

import edu.bbte.idde.bfim2114.springbackend.model.Category;

public interface CategoryRepository extends BaseRepository<Category> {
    Category findByName(String name);
}
