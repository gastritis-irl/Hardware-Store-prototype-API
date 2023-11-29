package edu.bbte.idde.bfim2114.backend.service;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import edu.bbte.idde.bfim2114.backend.repository.HardwareRepository;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class HardwareServiceImpl implements HardwareService {

    private final HardwareRepository hardwareRepository = RepositoryFactory.getInstance().getHardwareRepository();
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public HardwarePart findByName(String partName) {
        return hardwareRepository.findByPartName(partName);
    }

    @Override
    public HardwarePart findById(Long partId) {
        return hardwareRepository.findById(partId);
    }

    @Override
    public boolean isValid(HardwarePart part) {
        Long userId = part.getId();

        return userService.existsById(userId) && part.getName() != null
                && !part.getName().isEmpty() && part.getCategory() != null
                && !part.getCategory().isEmpty() && part.getManufacturer() != null
                && !part.getManufacturer().isEmpty()
                && part.getPrice() != null && part.getDescription() != null && !part.getDescription().isEmpty();
    }

    @Override
    public HardwarePart create(HardwarePart part) {
        if (!isValid(part)) {
            log.warn("Invalid HardwarePart: {}", part);
            throw new IllegalArgumentException("Invalid HardwarePart");
        }

        return hardwareRepository.create(part);
    }

    @Override
    public void delete(Long partId) {
        HardwarePart part = hardwareRepository.findById(partId);
        if (part != null) {
            hardwareRepository.deleteById(part.getId());
        }
    }

    @Override
    public HardwarePart update(HardwarePart part) {
        if (!isValid(part)) {
            log.warn("Invalid HardwarePart: {}", part);
            throw new IllegalArgumentException("Invalid HardwarePart");
        }
        return hardwareRepository.update(part);
    }

    @Override
    public Collection<HardwarePart> findAll() {
        return hardwareRepository.findAll();
    }
}