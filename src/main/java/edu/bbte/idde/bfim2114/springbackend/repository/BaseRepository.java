package edu.bbte.idde.bfim2114.springbackend.repository;


import edu.bbte.idde.bfim2114.springbackend.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
