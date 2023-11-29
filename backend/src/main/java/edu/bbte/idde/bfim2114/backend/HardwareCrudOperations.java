package edu.bbte.idde.bfim2114.backend;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
public final class HardwareCrudOperations implements CrudOperations<HardwarePart> {

    private final Map<Long, HardwarePart> dataStore = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    private HardwareCrudOperations() {
    }

    private static class Holder {
        private static final HardwareCrudOperations INSTANCE = new HardwareCrudOperations();
    }

    public static HardwareCrudOperations getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public HardwarePart create(HardwarePart part) {
        long id = currentId.getAndIncrement();
        part.setId(id);
        dataStore.put(id, part);
        log.info("HardwarePart with ID {} created.", id);
        return part;
    }

    @Override
    public Optional<HardwarePart> read(Long id) {
        HardwarePart part = dataStore.get(id);
        if (part == null) {
            log.warn("HardwarePart with ID {} not found.", id);
        } else {
            log.info("HardwarePart with ID {} found.", id);
        }
        return Optional.ofNullable(part);
    }

    @Override
    public List<HardwarePart> readAll() {
        log.info("Reading all HardwareParts.");
        return new ArrayList<>(dataStore.values());
    }

    @Override
    public HardwarePart update(HardwarePart part) {
        if (dataStore.containsKey(part.getId())) {
            dataStore.put(part.getId(), part);
            log.info("HardwarePart with ID {} updated.", part.getId());
            return part;
        } else {
            log.error("Failed to update. HardwarePart with ID {} not found.", part.getId());
            throw new IllegalArgumentException("HardwarePart with the given ID does not exist");
        }
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting HardwarePart with ID {}.", id);
        return dataStore.remove(id) != null;
    }
}
