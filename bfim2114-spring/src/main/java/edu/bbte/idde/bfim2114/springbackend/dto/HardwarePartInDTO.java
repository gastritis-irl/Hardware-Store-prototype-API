package edu.bbte.idde.bfim2114.springbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HardwarePartInDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String manufacturer;

    private String category;

    @Min(value = 0, message = "Price must be greater or equal than 0")
    private Double price;

    private String description;

    @Min(value = 1, message = "User ID must be greater or equal than 1")
    private Long userId;
}
