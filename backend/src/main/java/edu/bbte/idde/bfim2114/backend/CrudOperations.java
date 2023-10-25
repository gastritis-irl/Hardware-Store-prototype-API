package edu.bbte.idde.bfim2114.backend;

import java.util.List;
import java.util.Optional;

public interface CrudOperations<T> {
    T create(T entity);

    Optional<T> read(Long id);

    List<T> readAll();

    T update(T entity);

    boolean delete(Long id);
}
