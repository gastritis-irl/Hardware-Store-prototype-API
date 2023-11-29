package edu.bbte.idde.bfim2114.backend.service;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;

import java.util.Collection;

public interface HardwareService {

    HardwarePart findByName(String partName);

    boolean isValid(HardwarePart part);

    HardwarePart findById(Long partId);

    HardwarePart create(HardwarePart part);

    void delete(Long partId);

    HardwarePart update(HardwarePart part);

    Collection<HardwarePart> findAll();
}
