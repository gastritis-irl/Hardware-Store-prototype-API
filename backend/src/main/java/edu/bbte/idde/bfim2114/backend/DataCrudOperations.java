package edu.bbte.idde.bfim2114.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DataCrudOperations implements CrudOperations<DataEntity> {

    private final ConcurrentHashMap<Long, DataEntity> dataStore = new ConcurrentHashMap<>();
    private Long idCounter = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCrudOperations.class);

    @Override
    public void create(DataEntity entity) {
        entity.setId(idCounter++);
        dataStore.put(entity.getId(), entity);
    }

    @Override
    public Optional<DataEntity> read(Long id) {
        return Optional.ofNullable(dataStore.get(id));
    }

    @Override
    public List<DataEntity> readAll() {
        return new ArrayList<>(dataStore.values());
    }

    @Override
    public void update(DataEntity entity) {
        if (entity.getId() == null || !dataStore.containsKey(entity.getId())) {
            LOGGER.error("Entity with the given ID does not exist");
            throw new IllegalArgumentException("Entity with the given ID does not exist");
        }
        dataStore.put(entity.getId(), entity);
    }

    @Override
    public boolean delete(Long id) {
        return dataStore.remove(id) != null;
    }
}
