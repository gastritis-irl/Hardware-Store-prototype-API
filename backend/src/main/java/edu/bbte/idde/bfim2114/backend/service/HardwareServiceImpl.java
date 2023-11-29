package edu.bbte.idde.bfim2114.backend.service;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import edu.bbte.idde.bfim2114.backend.repository.HardwareRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HardwareServiceImpl implements HardwareService {

    private final HardwareRepository hardwareRepository;

    public HardwareServiceImpl(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }

    @Override
    public void addHardware(String partName, String partType, String partManufacturer, int partPrice, int partQuantity) {
        HardwarePart part = new HardwarePart();
        part.setName(partName);
        part.setCategory(partType);
        part.setManufacturer(partManufacturer);
        part.setPrice((double) partPrice);
        part.setDescription(String.valueOf(partQuantity));
        hardwareRepository.create(part);
    }

    @Override
    public void deleteHardware(String partName) {
        HardwarePart part = hardwareRepository.findByPartName(partName);
        if (part != null) {
            hardwareRepository.deleteById(part.getId());
        }
    }

    @Override
    public void updateHardware(String partName, String partType, String partManufacturer, int partPrice, int partQuantity) {
        HardwarePart part = hardwareRepository.findByPartName(partName);
        if (part != null) {
            part.setName(partName);
            part.setCategory(partType);
            part.setManufacturer(partManufacturer);
            part.setPrice((double) partPrice);
            part.setDescription(String.valueOf(partQuantity));
            hardwareRepository.update(part);
        }
    }

    @Override
    public void listHardware() {
        for (HardwarePart part : hardwareRepository.findAll()) {
            log.info(part.toString());
        }
    }

    @Override
    public void listHardware(String partName) {
        HardwarePart part = hardwareRepository.findByPartName(partName);
        if (part != null) {
            log.info(part.toString());
        }
    }

    @Override
    public void listHardware(String partName, String partType) {
        for (HardwarePart part : hardwareRepository.findAll()) {
            if (part.getName().equals(partName) && part.getCategory().equals(partType)) {
                log.info(part.toString());
            }
        }
    }

    @Override
    public void listHardware(String partName, String partType, String partManufacturer) {
        for (HardwarePart part : hardwareRepository.findAll()) {
            if (part.getName().equals(partName) && part.getCategory().equals(partType) && part.getManufacturer().equals(partManufacturer)) {
                log.info(part.toString());
            }
        }
    }

    @Override
    public void listHardware(String partName, String partType, String partManufacturer, int partPrice) {
        for (HardwarePart part : hardwareRepository.findAll()) {
            if (part.getName().equals(partName) && part.getCategory().equals(partType) && part.getManufacturer().equals(partManufacturer) && part.getPrice() == partPrice) {
                log.info(part.toString());
            }
        }
    }

    @Override
    public void listHardware(String partName, String partType, String partManufacturer, int partPrice, int partQuantity) {
        for (HardwarePart part : hardwareRepository.findAll()) {
            if (part.getName().equals(partName) && part.getCategory().equals(partType) && part.getManufacturer().equals(partManufacturer) && part.getPrice() == partPrice && part.getDescription().equals(String.valueOf(partQuantity))) {
                log.info(part.toString());
            }
        }
    }
}