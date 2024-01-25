package edu.bbte.idde.bfim2114.springbackend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HardwarePartOutDTO implements Serializable {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String manufacturer;
    private String categoryName;
    private Double price;
    private String description;
    private Long userId;
}
