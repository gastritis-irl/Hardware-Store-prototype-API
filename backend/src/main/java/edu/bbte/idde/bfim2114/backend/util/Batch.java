package edu.bbte.idde.bfim2114.backend.util;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
public class Batch implements Serializable {

    public final Collection<HardwarePart> batch;
}
