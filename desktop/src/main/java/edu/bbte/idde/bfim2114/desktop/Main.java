package edu.bbte.idde.bfim2114.desktop;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import edu.bbte.idde.bfim2114.backend.service.HardwareService;
import edu.bbte.idde.bfim2114.backend.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {

    private static final HardwareService OPERATIONS = ServiceFactory.getInstance().getHardwareService();
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = SCANNER.nextInt();
            SCANNER.nextLine();

            switch (choice) {
                case 1:
                    createHardwarePart();
                    break;
                case 2:
                    readHardwarePartById();
                    break;
                case 3:
                    readAllHardwareParts();
                    break;
                case 4:
                    updateHardwarePart();
                    break;
                case 5:
                    deleteHardwarePartById();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    LOGGER.warn("Invalid choice!");
                    break;
            }
        }
        SCANNER.close();
    }

    private static void displayMenu() {
        LOGGER.info("Select an option:");
        LOGGER.info("1. Create Hardware Part");
        LOGGER.info("2. Read Hardware Part by ID");
        LOGGER.info("3. Read All Hardware Parts");
        LOGGER.info("4. Update Hardware Part");
        LOGGER.info("5. Delete Hardware Part by ID");
        LOGGER.info("6. Exit");
    }

    private static void createHardwarePart() {
        HardwarePart part = new HardwarePart();
        LOGGER.info("Enter name: ");
        part.setName(SCANNER.nextLine());
        LOGGER.info("Enter manufacturer: ");
        part.setManufacturer(SCANNER.nextLine());
        LOGGER.info("Enter category: ");
        part.setCategory(SCANNER.nextLine());
        LOGGER.info("Enter price: ");
        part.setPrice(SCANNER.nextDouble());
        SCANNER.nextLine();  // Consume newline left-over
        LOGGER.info("Enter description: ");
        part.setDescription(SCANNER.nextLine());
        OPERATIONS.create(part);
        LOGGER.info("Hardware Part created with ID: {}", part.getId());
    }

    private static void readHardwarePartById() {
        LOGGER.info("Enter ID: ");
        long id = SCANNER.nextLong();
        OPERATIONS.findById(id);
    }

    private static void readAllHardwareParts() {
        OPERATIONS.findAll().forEach(data -> LOGGER.info(data.toString()));
    }

    private static void updateHardwarePart() {
        LOGGER.info("Enter ID for update: ");
        long id = SCANNER.nextLong();
        SCANNER.nextLine();
        HardwarePart updatedPart = new HardwarePart();
        updatedPart.setId(id);
        LOGGER.info("Enter new name: ");
        updatedPart.setName(SCANNER.nextLine());
        LOGGER.info("Enter new manufacturer: ");
        updatedPart.setManufacturer(SCANNER.nextLine());
        LOGGER.info("Enter new category: ");
        updatedPart.setCategory(SCANNER.nextLine());
        LOGGER.info("Enter new price: ");
        updatedPart.setPrice(SCANNER.nextDouble());
        SCANNER.nextLine();  // Consume newline left-over
        LOGGER.info("Enter new description: ");
        updatedPart.setDescription(SCANNER.nextLine());
        OPERATIONS.update(updatedPart);
        LOGGER.info("Hardware Part updated.");
    }

    private static void deleteHardwarePartById() {
        LOGGER.info("Enter ID to delete: ");
        long id = SCANNER.nextLong();
        OPERATIONS.delete(id);
    }
}
