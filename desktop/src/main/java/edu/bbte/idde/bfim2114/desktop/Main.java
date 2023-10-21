package edu.bbte.idde.bfim2114.desktop;

import edu.bbte.idde.bfim2114.backend.DataCrudOperations;
import edu.bbte.idde.bfim2114.backend.DataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final DataCrudOperations operations = new DataCrudOperations();
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            LOGGER.info("Select an option:");
            LOGGER.info("1. Create");
            LOGGER.info("2. Read");
            LOGGER.info("3. Read All");
            LOGGER.info("4. Update");
            LOGGER.info("5. Delete");
            LOGGER.info("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();
            String value;
            long id;

            switch (choice) {
                case 1:
                    LOGGER.info("Enter value: ");
                    value = scanner.nextLine();
                    DataEntity entity = new DataEntity(null, value);
                    operations.create(entity);
                    LOGGER.info("Data created with ID: {}", entity.getId());
                    break;
                case 2:
                    LOGGER.info("Enter ID: ");
                    id = scanner.nextLong();
                    operations.read(id).ifPresentOrElse(
                            data -> LOGGER.info("Data (ID: " + data.getId() + ", Value: " + data.getValue() + ")"),
                            () -> LOGGER.warn("No data found with given ID.")
                    );
                    break;
                case 3:
                    operations.readAll().forEach(data -> LOGGER.info("Data (ID: "
                            + data.getId() + ", Value: " + data.getValue() + ")"));
                    break;
                case 4:
                    LOGGER.info("Enter ID: ");
                    id = scanner.nextLong();
                    scanner.nextLine();
                    LOGGER.info("Enter new value: ");
                    value = scanner.nextLine();
                    DataEntity updatedEntity = new DataEntity(id, value);
                    operations.update(updatedEntity);
                    LOGGER.info("Data updated.");
                    break;
                case 5:
                    LOGGER.info("Enter ID to delete: ");
                    id = scanner.nextLong();
                    if (operations.delete(id)) {
                        LOGGER.info("Data deleted.");
                    } else {
                        LOGGER.error("Deletion failed. No data found with given ID.");
                    }
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
}
