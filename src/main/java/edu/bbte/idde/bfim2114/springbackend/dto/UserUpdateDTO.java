package edu.bbte.idde.bfim2114.springbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.checkerframework.checker.index.qual.Positive;

import java.io.Serializable;

@Data
public class UserUpdateDTO implements Serializable {

    @Positive
    private Long id;

    @NotBlank(message = "Email cannot be blank")
    private String email;
}
