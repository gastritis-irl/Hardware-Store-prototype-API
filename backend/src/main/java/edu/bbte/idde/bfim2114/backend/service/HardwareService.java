package edu.bbte.idde.bfim2114.backend.service;

public interface HardwareService {

    void addHardware(String partName, String partType, String partManufacturer, int partPrice, int partQuantity);
    void deleteHardware(String partName);
    void updateHardware(String partName, String partType, String partManufacturer, int partPrice, int partQuantity);
    void listHardware();
    void listHardware(String partName);
    void listHardware(String partName, String partType);
    void listHardware(String partName, String partType, String partManufacturer);
    void listHardware(String partName, String partType, String partManufacturer, int partPrice);
    void listHardware(String partName, String partType, String partManufacturer, int partPrice, int partQuantity);
}
