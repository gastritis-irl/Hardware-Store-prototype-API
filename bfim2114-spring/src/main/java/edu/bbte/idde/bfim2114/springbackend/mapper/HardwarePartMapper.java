package edu.bbte.idde.bfim2114.springbackend.mapper;

import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartInDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartOutDTO;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface HardwarePartMapper {
    @Mapping(source = "userId", target = "user.id")
    HardwarePart hardwareParttoDTO(HardwarePartInDTO hardwarePartInDTO);

    @Mapping(source = "user.id", target = "userId")
    HardwarePartOutDTO dtoToHardwarePart(HardwarePart hardwarePart);
}
