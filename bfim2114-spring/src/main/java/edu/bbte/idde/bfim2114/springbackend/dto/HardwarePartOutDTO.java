package edu.bbte.idde.bfim2114.springbackend.dto;

import lombok.Data;

@Data
public class HardwarePartOutDTO {

    private Long id;
    private String name;
    private String manufacturer;
    private String category;
    private Double price;
    private String description;
    private Long userId;
}
