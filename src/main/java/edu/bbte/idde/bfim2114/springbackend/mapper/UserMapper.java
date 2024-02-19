package edu.bbte.idde.bfim2114.springbackend.mapper;

import edu.bbte.idde.bfim2114.springbackend.dto.UserOutDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.UserUpdateDTO;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import edu.bbte.idde.bfim2114.springbackend.service.UserService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = HardwarePartMapper.class)
public abstract class UserMapper {

    @Autowired
    private UserService userService;

    public abstract User dtoToUser(UserUpdateDTO userUpdateDTO);

    public abstract UserOutDTO userToDTo(User user);

    @AfterMapping
    protected void handleDtoToEntityMapping(UserUpdateDTO dto, @MappingTarget User entity) {
        User user = userService.findById(dto.getId());
        entity.setRole(user.getRole());
        entity.setEmail(dto.getEmail());
        entity.setPassword(user.getPassword());
        entity.setThemeId(user.getThemeId());
    }

}
