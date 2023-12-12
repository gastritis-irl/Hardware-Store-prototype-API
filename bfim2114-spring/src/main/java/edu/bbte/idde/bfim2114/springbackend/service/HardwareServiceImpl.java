package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.repository.HardwareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HardwareServiceImpl implements HardwareService {

    private final HardwareRepository hardwareRepository;
    private final UserService userService;

    @Override
    public HardwarePart findByName(String partName) {

        log.info("Finding HardwarePart by name: {}", partName);
        return hardwareRepository.findByName(partName);
    }

    @Override
    public Optional<HardwarePart> findById(Long partId) {

        log.info("Finding HardwarePart by id: {}", partId);
        return hardwareRepository.findById(partId);
    }

    @Override
    public boolean isValid(HardwarePart part) {
        if (part == null) {
            log.error("HardwarePart is null so it is invalid");
            return true;
        }
        Long userId = part.getUser().getId();

        return !userService.existsById(userId) || part.getName() == null
            || part.getName().isEmpty();
    }

    @Override
    public HardwarePart create(HardwarePart part) {
        if (isValid(part)) {
            log.error("Invalid HardwarePart");
            throw new IllegalArgumentException("Invalid HardwarePart");
        }

        log.info("Creating HardwarePart: {}", part);
        return hardwareRepository.save(part);
    }

    @Override
    public void delete(Long partId) {

        log.info("Deleting HardwarePart by id: {}", partId);
        hardwareRepository.deleteById(partId);
    }

    @Override
    public HardwarePart update(HardwarePart part) {
        if (isValid(part)) {
            log.error("Invalid HardwarePart");
            throw new IllegalArgumentException("Invalid HardwarePart");
        }
        log.info("Updating HardwarePart: {}", part);
        return hardwareRepository.save(part);
    }

    @Override
    public List<HardwarePart> findAll() {

        log.info("Finding all HardwareParts");
        return hardwareRepository.findAll();
    }
}
