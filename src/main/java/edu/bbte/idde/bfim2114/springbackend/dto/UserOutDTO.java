package edu.bbte.idde.bfim2114.springbackend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserOutDTO implements Serializable {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String email;
    private String role;
    private Long themeId;
    private String token;
}
