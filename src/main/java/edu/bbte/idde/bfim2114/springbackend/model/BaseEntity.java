package edu.bbte.idde.bfim2114.springbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @CreatedDate
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedDate;
}
