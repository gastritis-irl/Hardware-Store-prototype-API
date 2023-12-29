package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartPageDTO;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Optional;

public interface HardwareService {

    boolean isValid(HardwarePart part);

    Optional<HardwarePart> findById(Long partId);

    HardwarePart create(HardwarePart part);

    void delete(Long partId);

    HardwarePart update(HardwarePart part);

    Collection<HardwarePart> findAll();

    HardwarePartPageDTO findAllWithFilters(int page, Specification<HardwarePart> spec, String sortBy,
                                           String direction, Double minPrice, Double maxPrice,
                                           String textSearch, Long userId);
}
