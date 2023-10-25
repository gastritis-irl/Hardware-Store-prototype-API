package edu.bbte.idde.bfim2114.desktop;

import edu.bbte.idde.bfim2114.backend.HardwareCrudOperations;
import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final HardwareCrudOperations operations = new HardwareCrudOperations();
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

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
        scanner.close();
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
        part.setName(scanner.nextLine());
        LOGGER.info("Enter manufacturer: ");
        part.setManufacturer(scanner.nextLine());
        LOGGER.info("Enter category: ");
        part.setCategory(scanner.nextLine());
        LOGGER.info("Enter price: ");
        part.setPrice(scanner.nextDouble());
        scanner.nextLine();  // Consume newline left-over
        LOGGER.info("Enter description: ");
        part.setDescription(scanner.nextLine());
        operations.create(part);
        LOGGER.info("Hardware Part created with ID: {}", part.getId());
    }

    private static void readHardwarePartById() {
        LOGGER.info("Enter ID: ");
        long id = scanner.nextLong();
        operations.read(id).ifPresentOrElse(data -> LOGGER.info(data.toString()),
                () -> LOGGER.warn("No hardware part found with given ID."));
    }

    private static void readAllHardwareParts() {
        operations.readAll().forEach(data -> LOGGER.info(data.toString()));
    }

    private static void updateHardwarePart() {
        LOGGER.info("Enter ID for update: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        HardwarePart updatedPart = new HardwarePart();
        updatedPart.setId(id);
        LOGGER.info("Enter new name: ");
        updatedPart.setName(scanner.nextLine());
        LOGGER.info("Enter new manufacturer: ");
        updatedPart.setManufacturer(scanner.nextLine());
        LOGGER.info("Enter new category: ");
        updatedPart.setCategory(scanner.nextLine());
        LOGGER.info("Enter new price: ");
        updatedPart.setPrice(scanner.nextDouble());
        scanner.nextLine();  // Consume newline left-over
        LOGGER.info("Enter new description: ");
        updatedPart.setDescription(scanner.nextLine());
        operations.update(updatedPart);
        LOGGER.info("Hardware Part updated.");
    }

    private static void deleteHardwarePartById() {
        LOGGER.info("Enter ID to delete: ");
        long id = scanner.nextLong();
        if (operations.delete(id)) {
            LOGGER.info("Hardware Part deleted.");
        } else {
            LOGGER.error("Deletion failed. No hardware part found with given ID.");
        }
    }
}
