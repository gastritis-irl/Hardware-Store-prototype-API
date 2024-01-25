package edu.bbte.idde.bfim2114.springbackend.repository;

import edu.bbte.idde.bfim2114.springbackend.model.Category;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("category")
public interface CategoryRepository extends BaseRepository<Category> {

    Category findByName(String name);
}
