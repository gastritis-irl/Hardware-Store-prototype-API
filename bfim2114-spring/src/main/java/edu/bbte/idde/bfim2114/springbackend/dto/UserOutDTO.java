package edu.bbte.idde.bfim2114.springbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserOutDTO {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String email;
}
