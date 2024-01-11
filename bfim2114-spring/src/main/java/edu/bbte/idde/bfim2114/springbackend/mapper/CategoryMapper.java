package edu.bbte.idde.bfim2114.springbackend.mapper;

import edu.bbte.idde.bfim2114.springbackend.dto.CategoryPageDTO;
import edu.bbte.idde.bfim2114.springbackend.model.Category;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryPageDTO toCategoryPageDTO(Collection<Category> categories, int nrOfPages, long nrOfElements);
}
