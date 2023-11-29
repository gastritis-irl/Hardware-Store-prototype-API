package edu.bbte.idde.bfim2114.backend.repository;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;

public interface HardwareRepository extends BaseRepository<HardwarePart> {

    HardwarePart findByPartName(String partName) throws RepositoryException;

    HardwarePart findById(Long id) throws RepositoryException;

    void deleteById(Long id) throws RepositoryException;

    HardwarePart create(HardwarePart entity) throws RepositoryException;

    HardwarePart update(HardwarePart entity) throws RepositoryException;

}
