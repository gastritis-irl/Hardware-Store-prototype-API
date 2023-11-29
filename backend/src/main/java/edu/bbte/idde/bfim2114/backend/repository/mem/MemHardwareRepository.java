package edu.bbte.idde.bfim2114.backend.repository.mem;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import edu.bbte.idde.bfim2114.backend.repository.HardwareRepository;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryException;
import edu.bbte.idde.bfim2114.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
public final class MemHardwareRepository implements HardwareRepository {

    private final Map<Long, HardwarePart> dataStore = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    private final UserRepository userRepository = MemUserRepository.getInstance();

    private MemHardwareRepository() {
    }

    private static class Holder {
        private static final MemHardwareRepository INSTANCE = new MemHardwareRepository();
    }

    public static MemHardwareRepository getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public HardwarePart create(HardwarePart part) {

        if (part == null) {
            log.error("Failed to create. HardwarePart is null.");
            throw new IllegalArgumentException("HardwarePart is null");
        }

        long id = currentId.getAndIncrement();
        part.setId(id);
        Long userId = part.getUserId();
        if (userRepository.findById(userId) == null) {
            log.error("Failed to create. User with ID {} not found.", userId);
            throw new IllegalArgumentException("User with the given ID does not exist");
        }
        dataStore.put(id, part);
        log.info("HardwarePart with ID {} created.", id);
        return part;
    }

    @Override
    public HardwarePart findByPartName(String partName) throws RepositoryException {
        return dataStore.values().stream()
                .filter(part -> part.getName().equals(partName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public HardwarePart findById(Long id) {
        HardwarePart part = dataStore.get(id);
        if (part == null) {
            log.warn("HardwarePart with ID {} not found.", id);
        } else {
            log.info("HardwarePart with ID {} found.", id);
        }
        return part;
    }

    @Override
    public List<HardwarePart> findAll() {
        log.info("Reading all HardwareParts.");
        return new ArrayList<>(dataStore.values());
    }

    @Override
    public HardwarePart update(HardwarePart part) {
        if (dataStore.containsKey(part.getId())) {

            Long userId = part.getUserId();
            if (userRepository.findById(userId) == null) {
                log.error("Failed to update. User with ID {} not found.", userId);
                throw new IllegalArgumentException("User with the given ID does not exist");
            }

            dataStore.put(part.getId(), part);
            log.info("HardwarePart with ID {} updated.", part.getId());
            return part;
        } else {
            log.error("Failed to update. HardwarePart with ID {} not found.", part.getId());
            throw new IllegalArgumentException("HardwarePart with the given ID does not exist");
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting HardwarePart with ID {}.", id);
        dataStore.remove(id);
    }
}
