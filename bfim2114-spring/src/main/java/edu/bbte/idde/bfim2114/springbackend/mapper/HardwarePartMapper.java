package edu.bbte.idde.bfim2114.springbackend.mapper;

import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartInDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartOutDTO;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class HardwarePartMapper {

    @Autowired
    private UserService userService;

    @Mapping(source = "user.id", target = "userId")
    public abstract HardwarePartOutDTO hardwarePartToDTO(HardwarePart part);

    //    @Mapping(source = "userId", target = "user.id")
    public abstract HardwarePart dtoToHardwarePart(HardwarePartInDTO inDTO);

    @AfterMapping
    protected void handleDtoToEntityMapping(HardwarePartInDTO dto, @MappingTarget HardwarePart entity) {
        if (dto.getUserId() == null) {
            entity.setUser(null);
        } else {
            log.info("{}", userService.findById(dto.getUserId()));
            entity.setUser(userService.findById(dto.getUserId()));
            log.info("{}", entity.getUser());
        }
    }
}
