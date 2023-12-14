package edu.bbte.idde.bfim2114.backend.repository;

import edu.bbte.idde.bfim2114.backend.model.BaseEntity;

import java.util.Collection;

public interface BaseRepository<T extends BaseEntity> {
    T create(T entity);

    T findById(Long id);

    Collection<T> findAll();

    void deleteById(Long id);
}


