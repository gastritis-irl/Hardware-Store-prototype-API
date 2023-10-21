
package edu.bbte.idde.bfim2114.desktop;

import edu.bbte.idde.bfim2114.backend.DataCrudOperations;
import edu.bbte.idde.bfim2114.backend.DataEntity;

import java.util.Scanner;

public class Main {
    private static final DataCrudOperations operations = new DataCrudOperations();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Select an option:");
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Read All");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();
            String value;
            long id;

            switch (choice) {
                case 1:
                    System.out.print("Enter value: ");
                    value = scanner.nextLine();
                    DataEntity entity = new DataEntity(null, value);
                    operations.create(entity);
                    System.out.println("Data created with ID: " + entity.getId());
                    break;
                case 2:
                    System.out.print("Enter ID: ");
                    id = scanner.nextLong();
                    operations.read(id).ifPresentOrElse(
                            data -> System.out.println("Data (ID: " + data.getId() + ", Value: " + data.getValue() + ")"),
                            () -> System.out.println("No data found with given ID.")
                    );
                    break;
                case 3:
                    operations.readAll().forEach(data -> System.out.println("Data (ID: " + data.getId() + ", Value: " + data.getValue() + ")"));
                    break;
                case 4:
                    System.out.print("Enter ID: ");
                    id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Enter new value: ");
                    value = scanner.nextLine();
                    DataEntity updatedEntity = new DataEntity(id, value);
                    operations.update(updatedEntity);
                    System.out.println("Data updated.");
                    break;
                case 5:
                    System.out.print("Enter ID to delete: ");
                    id = scanner.nextLong();
                    if (operations.delete(id)) {
                        System.out.println("Data deleted.");
                    } else {
                        System.out.println("Deletion failed. No data found with given ID.");
                    }
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }
}
