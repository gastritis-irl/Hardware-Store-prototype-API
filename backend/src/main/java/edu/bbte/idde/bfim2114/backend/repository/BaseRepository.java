package edu.bbte.idde.bfim2114.backend.repository;

import edu.bbte.idde.bfim2114.backend.model.BaseEntity;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseRepository <T extends BaseEntity>{
    T create(T entity) throws RepositoryException;
    T findById(Long id) throws RepositoryException;
    Collection<T> findAll() throws RepositoryException;
    void deleteById(Long id) throws RepositoryException;
}


