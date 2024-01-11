package edu.bbte.idde.bfim2114.springbackend.dto;

import edu.bbte.idde.bfim2114.springbackend.model.Category;
import lombok.Data;

import java.util.Collection;

@Data
public class CategoryPageDTO {

    private Collection<Category> categories;
    private int nrOfPages;
    private long nrOfElements;
}
