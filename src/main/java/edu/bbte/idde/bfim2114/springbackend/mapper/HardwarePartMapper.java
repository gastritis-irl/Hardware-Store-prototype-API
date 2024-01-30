package edu.bbte.idde.bfim2114.springbackend.mapper;

import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartInDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartOutDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartPageDTO;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.service.CategoryService;
import edu.bbte.idde.bfim2114.springbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class HardwarePartMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "user.id", target = "userId")
    public abstract HardwarePartOutDTO hardwarePartToDTO(HardwarePart part);

    public abstract HardwarePart dtoToHardwarePart(HardwarePartInDTO inDTO);

    public abstract HardwarePartPageDTO hardwarePartPageToDTO(Collection<HardwarePart> hardwareParts,
                                                              int nrOfPages, long nrOfElements);

    @AfterMapping
    protected void handleDtoToEntityMapping(HardwarePartInDTO dto, @MappingTarget HardwarePart entity) {
        if (dto.getUserId() == null) {
            entity.setUser(null);
        } else {
            log.info("{}", userService.findById(dto.getUserId()));
            entity.setUser(userService.findById(dto.getUserId()));
            log.info("{}", entity.getUser());
        }
        if (dto.getCategoryName() == null) {
            entity.setCategory(null);
        } else {
            entity.setCategory(categoryService.findByName(dto.getCategoryName()));
        }
    }

    @AfterMapping
    protected void handleEntityToDtoMapping(HardwarePart entity, @MappingTarget HardwarePartOutDTO dto) {
        if (entity.getUser() == null) {
            dto.setUserId(null);
        } else {
            dto.setUserId(entity.getUser().getId());
        }
        if (entity.getCategory() == null) {
            dto.setCategoryName(null);
        } else {
            dto.setCategoryName(entity.getCategory().getName());
        }
    }
}
