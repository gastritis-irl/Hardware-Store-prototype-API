package edu.bbte.idde.bfim2114.springbackend.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class HardwarePartPageDTO {

    Collection<HardwarePartOutDTO> hardwareParts;
    int nrOfPages;
    long nrOfElements;
}
