package edu.bbte.idde.bfim2114.backend.service;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import edu.bbte.idde.bfim2114.backend.repository.HardwareRepository;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryException;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class HardwareServiceImpl implements HardwareService {

    private final HardwareRepository hardwareRepository = RepositoryFactory.getInstance().getHardwareRepository();
    private final UserService userService;

    public HardwareServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HardwarePart findByName(String partName) {
        try {
            return hardwareRepository.findByPartName(partName);
        } catch (RepositoryException e) {
            log.error("Error while finding HardwarePart by name: {}", partName, e);
            throw new ServiceException("Error while finding HardwarePart by name", e);
        }
    }

    @Override
    public HardwarePart findById(Long partId) {
        try {
            return hardwareRepository.findById(partId);
        } catch (RepositoryException e) {
            log.error("Error while finding HardwarePart by id: {}", partId, e);
            throw new ServiceException("Error while finding HardwarePart by id", e);
        }
    }

    @Override
    public boolean isValid(HardwarePart part) {
        try {
            if (part == null) {
                return false;
            }
            Long userId = part.getUserId();

            return userService.existsById(userId) && part.getName() != null
                    && !part.getName().isEmpty();
        } catch (RepositoryException e) {
            log.error("Error while checking if HardwarePart is valid: {}", part, e);
            throw new ServiceException("Error while checking if HardwarePart is valid", e);
        }
    }

    @Override
    public HardwarePart create(HardwarePart part) {
        try {
            if (!isValid(part)) {
                log.warn("Invalid HardwarePart: {}", part);
                throw new IllegalArgumentException("Invalid HardwarePart");
            }

            return hardwareRepository.create(part);
        } catch (RepositoryException e) {
            log.error("Error while creating HardwarePart: {}", part, e);
            throw new ServiceException("Error while creating HardwarePart", e);
        }
    }

    @Override
    public void delete(Long partId) {
        try {
            HardwarePart part = hardwareRepository.findById(partId);
            if (part != null) {
                hardwareRepository.deleteById(part.getId());
            }
        } catch (RepositoryException e) {
            log.error("Error while deleting HardwarePart with id: {}", partId, e);
            throw new ServiceException("Error while deleting HardwarePart", e);
        }
    }

    @Override
    public HardwarePart update(HardwarePart part) {
        try {
            if (!isValid(part)) {
                log.warn("Invalid HardwarePart: {}", part);
                throw new IllegalArgumentException("Invalid HardwarePart");
            }
            return hardwareRepository.update(part);
        } catch (RepositoryException e) {
            log.error("Error while updating HardwarePart: {}", part, e);
            throw new ServiceException("Error while updating HardwarePart", e);
        }
    }

    @Override
    public Collection<HardwarePart> findAll() {
        try {
            return hardwareRepository.findAll();
        } catch (RepositoryException e) {
            log.error("Error while finding all HardwareParts", e);
            throw new ServiceException("Error while finding all HardwareParts", e);
        }
    }
}