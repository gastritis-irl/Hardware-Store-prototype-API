package edu.bbte.idde.bfim2114.springbackend.repository;

import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;


public interface HardwareRepository extends BaseRepository<HardwarePart> {

    HardwarePart findByName(String partName);
}
