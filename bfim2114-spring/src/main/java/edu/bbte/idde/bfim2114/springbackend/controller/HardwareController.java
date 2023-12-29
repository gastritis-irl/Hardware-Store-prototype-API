package edu.bbte.idde.bfim2114.springbackend.controller;

import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartInDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartOutDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartPageDTO;
import edu.bbte.idde.bfim2114.springbackend.mapper.HardwarePartMapper;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.service.HardwareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hardware")
public class HardwareController {

    private final HardwareService hardwareService;
    private final HardwarePartMapper hardwarePartMapper;

    @GetMapping("/all/test")
    public ResponseEntity<Collection<HardwarePartOutDTO>> getAllHardwareParts() {
        log.info("GET: /api/hardware/all/test");

        return ResponseEntity.ok(
            hardwareService.findAll().stream().map(hardwarePartMapper::hardwarePartToDTO).toList());
    }

    @GetMapping
    public ResponseEntity<HardwarePartPageDTO> getAllHardwareParts(
        @RequestParam("orderBy") Optional<String> orderBy,
        @RequestParam("direction") Optional<String> direction,
        @RequestParam("pageNumber") Optional<Integer> pageNumber,
        @RequestParam("MinPrice") Optional<Double> minPrice,
        @RequestParam("MaxPrice") Optional<Double> maxPrice,
        @RequestParam("TextSearch") Optional<String> textSearch,
        @RequestParam("userId") Optional<Long> userId
    ) {
        // log the params
        log.info("GET: /api/hardware");
        log.info("orderBy: {}", orderBy.orElse(null));
        log.info("direction: {}", direction.orElse(null));
        log.info("pageNumber: {}", pageNumber.orElse(1));
        log.info("MinPrice: {}", minPrice.orElse(null));
        log.info("MaxPrice: {}", maxPrice.orElse(null));
        log.info("TextSearch: {}", textSearch.orElse(null));
        log.info("userId: {}", userId.orElse(null));

        Specification<HardwarePart> spec = Specification.where(null);
        return ResponseEntity.ok(
            hardwareService.findAllWithFilters(
                pageNumber.orElse(1),
                spec,
                orderBy.orElse(null),
                direction.orElse(null),
                minPrice.orElse(0.0),
                maxPrice.orElse(null),
                textSearch.orElse(null),
                userId.orElse(null)
            ));
    }

    @PostMapping
    public ResponseEntity<HardwarePartOutDTO> createHardwarePart(@Valid @RequestBody
                                                                 HardwarePartInDTO hardwarePartInDTO) {
        log.info("POST: /api/hardware");
        HardwarePart hardwarePart = hardwarePartMapper.dtoToHardwarePart(hardwarePartInDTO);
        log.info("{}", hardwarePart);
        return ResponseEntity.ok(hardwarePartMapper.hardwarePartToDTO(hardwareService.create(hardwarePart)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HardwarePartOutDTO> getHardwarePartById(@PathVariable Long id) {
        log.info("GET: /api/hardware/{}", id);
        if (hardwareService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hardwarePartMapper.hardwarePartToDTO(hardwareService.findById(id).get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HardwarePartOutDTO> updateHardwarePart(
        @PathVariable Long id,
        @Valid @RequestBody HardwarePartInDTO hardwarePartInDTO
    ) {
        log.info("PUT: /api/hardware/{}", id);
        HardwarePart hardwarePart = hardwarePartMapper.dtoToHardwarePart(hardwarePartInDTO);
        hardwarePart.setId(id);
        HardwarePart updatedHardwarePart = hardwareService.update(hardwarePart);
        if (updatedHardwarePart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hardwarePartMapper.hardwarePartToDTO(updatedHardwarePart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHardwarePart(@PathVariable Long id) {
        log.info("DELETE: /api/hardware/{}", id);
        hardwareService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
