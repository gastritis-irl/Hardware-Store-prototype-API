package edu.bbte.idde.bfim2114.backend.repository;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;

public interface HardwareRepository extends BaseRepository<HardwarePart> {

    HardwarePart findByPartName(String partName);

    @Override
    HardwarePart findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    HardwarePart create(HardwarePart entity);

    HardwarePart update(HardwarePart entity);

}
