package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;

import java.util.Collection;
import java.util.Optional;

public interface HardwareService {

    HardwarePart findByName(String partName);

    boolean isValid(HardwarePart part);

    Optional<HardwarePart> findById(Long partId);

    HardwarePart create(HardwarePart part);

    void delete(Long partId);

    HardwarePart update(HardwarePart part);

    Collection<HardwarePart> findAll();
}
