package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.repository.HardwareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HardwareServiceImpl implements HardwareService {

    private final HardwareRepository hardwareRepository;
    private final UserService userService;

    @Override
    public HardwarePart findByName(String partName) {
        return hardwareRepository.findByName(partName);
    }

    @Override
    public Optional<HardwarePart> findById(Long partId) {
        return hardwareRepository.findById(partId);
    }

    @Override
    public boolean isValid(HardwarePart part) {
        if (part == null) {
            return false;
        }
        Long userId = part.getUser().getId();

        return userService.existsById(userId) && part.getName() != null
                && !part.getName().isEmpty();
    }

    @Override
    public HardwarePart create(HardwarePart part) {
        if (!isValid(part)) {
            throw new IllegalArgumentException("Invalid HardwarePart");
        }

        return hardwareRepository.save(part);
    }

    @Override
    public void delete(Long partId) {
        hardwareRepository.deleteById(partId);
    }

    @Override
    public HardwarePart update(HardwarePart part) {
        if (!isValid(part)) {
            throw new IllegalArgumentException("Invalid HardwarePart");
        }
        return hardwareRepository.save(part);
    }

    @Override
    public List<HardwarePart> findAll() {
        return hardwareRepository.findAll();
    }
}
