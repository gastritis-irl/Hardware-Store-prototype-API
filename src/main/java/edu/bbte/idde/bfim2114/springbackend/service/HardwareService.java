package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartPageDTO;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.util.SpecificationFields;

import java.util.Collection;
import java.util.Optional;

public interface HardwareService {

    boolean isValid(HardwarePart part);

    Optional<HardwarePart> findById(Long partId);

    HardwarePart create(HardwarePart part);

    void delete(Long partId);

    HardwarePart update(HardwarePart part);

    Collection<HardwarePart> findAll();

    HardwarePartPageDTO findAllWithFilters(SpecificationFields specificationFields);
}
