package edu.bbte.idde.bfim2114.springbackend.repository;

import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.redis.core.RedisHash;

import java.util.Collection;

@RedisHash("HardwarePart")
public interface HardwareRepository extends BaseRepository<HardwarePart>, JpaSpecificationExecutor<HardwarePart> {

    Collection<HardwarePart> findAllByCategoryId(Long categoryId);
}
