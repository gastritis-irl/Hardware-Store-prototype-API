
package edu.bbte.idde.bfim2114.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataCrudOperations implements CrudOperations<DataEntity> {

    private final Map<Long, DataEntity> dataStore = new HashMap<>();
    private Long idCounter = 1L;

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
            throw new IllegalArgumentException("Entity with the given ID does not exist");
        }
        dataStore.put(entity.getId(), entity);
    }

    @Override
    public boolean delete(Long id) {
        return dataStore.remove(id) != null;
    }
}
