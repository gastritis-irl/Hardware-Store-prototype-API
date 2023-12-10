package edu.bbte.idde.bfim2114.springbackend.controller;

import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartInDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartOutDTO;
import edu.bbte.idde.bfim2114.springbackend.mapper.HardwarePartMapper;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.service.HardwareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hardware")
public class HardwareController {

    private final HardwareService hardwareService;
    private final HardwarePartMapper hardwarePartMapper;

    @GetMapping
    public ResponseEntity<Collection<HardwarePartOutDTO>> getAllHardwareParts() {
        log.info("GET: /api/hardware");
        return ResponseEntity.ok(hardwareService.findAll().stream()
            .map(hardwarePartMapper::dtoToHardwarePart)
            .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<HardwarePartOutDTO> createHardwarePart(@Valid @RequestBody HardwarePartInDTO hardwarePartInDTO) {
        log.info("POST: /api/hardware");
        HardwarePart hardwarePart = hardwarePartMapper.hardwareParttoDTO(hardwarePartInDTO);
        return ResponseEntity.ok(hardwarePartMapper.dtoToHardwarePart(hardwareService.create(hardwarePart)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HardwarePartOutDTO> getHardwarePartById(@PathVariable Long id) {
        log.info("GET: /api/hardware/{}", id);
        if (hardwareService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hardwarePartMapper.dtoToHardwarePart(hardwareService.findById(id).get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HardwarePartOutDTO> updateHardwarePart(@PathVariable Long id, @Valid @RequestBody HardwarePartInDTO hardwarePartInDTO) {
        log.info("PUT: /api/hardware/{}", id);
        HardwarePart hardwarePart = hardwarePartMapper.hardwareParttoDTO(hardwarePartInDTO);
        hardwarePart.setId(id);
        HardwarePart updatedHardwarePart = hardwareService.update(hardwarePart);
        if (updatedHardwarePart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hardwarePartMapper.dtoToHardwarePart(updatedHardwarePart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHardwarePart(@PathVariable Long id) {
        log.info("DELETE: /api/hardware/{}", id);
        hardwareService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
