package edu.bbte.idde.bfim2114.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class HardwarePart extends BaseEntity {

    private String name;
    private String manufacturer;
    private String category;
    private Double price;
    private String description;
}
