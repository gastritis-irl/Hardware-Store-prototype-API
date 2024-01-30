package edu.bbte.idde.bfim2114.springbackend.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ThemeDTO {

    @Size(min = 1, max = 3, message = "Theme id must be between 1 and 3")
    private Long themeId;
}
