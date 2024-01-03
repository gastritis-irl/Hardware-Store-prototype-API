package edu.bbte.idde.bfim2114.springbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HardwarePartInDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String manufacturer;

    private String categoryName;

    @Min(value = 0, message = "Price must be greater or equal than 0")
    private Double price;

    private String description;

    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be greater or equal than 1")
    private Long userId;
}
