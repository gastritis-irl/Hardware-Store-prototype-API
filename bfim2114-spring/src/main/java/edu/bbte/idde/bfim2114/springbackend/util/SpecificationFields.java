package edu.bbte.idde.bfim2114.springbackend.util;

import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;


@Data
public class SpecificationFields {

    private int page;
    private Specification<HardwarePart> spec;
    private String sortBy;
    private String direction;
    private Double minPrice;
    private Double maxPrice;
    private String textSearch;
    private Long userId;
    private String categoryName;
}
