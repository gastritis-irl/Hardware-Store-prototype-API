package edu.bbte.idde.bfim2114.springbackend.mapper;

import edu.bbte.idde.bfim2114.springbackend.dto.UserInDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.UserOutDTO;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToUser(UserInDTO userInDTO);

    UserOutDTO userToDTo(User user);
}
