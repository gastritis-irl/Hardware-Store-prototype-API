package edu.bbte.idde.bfim2114.backend;

import java.util.List;
import java.util.Optional;

public interface CrudOperations<T> {
    void create(T entity);

    Optional<T> read(Long id);

    List<T> readAll();

    void update(T entity);

    boolean delete(Long id);
}
