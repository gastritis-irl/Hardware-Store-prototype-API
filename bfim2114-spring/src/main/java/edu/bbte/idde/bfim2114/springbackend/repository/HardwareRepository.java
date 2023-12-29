package edu.bbte.idde.bfim2114.springbackend.repository;

import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HardwareRepository extends BaseRepository<HardwarePart>, JpaSpecificationExecutor<HardwarePart> {
}
