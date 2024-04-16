package org.example;

import Entity.InsuranceCategory;
import Service.InsuranceCatService;
import util.DataSource;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize your DataSource and Service instances
        DataSource ds1 = DataSource.getInstance();
        InsuranceCatService inscats = new InsuranceCatService();

        boolean exit = false;

        while (!exit) {
            System.out.println("Choose an operation:");
            System.out.println("1. Add");
            System.out.println("2. Update");
            System.out.println("3. Delete");
            System.out.println("4. View");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter category name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter category description:");
                    String description = scanner.nextLine();
                    InsuranceCategory inscatAdd = new InsuranceCategory(name, description);
                    inscats.AddCatIns(inscatAdd);
                    break;
                case 2:
                    System.out.println("Enter category ID to update:");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new category name:");
                    String newName = scanner.nextLine();
                    System.out.println("Enter new category description:");
                    String newDescription = scanner.nextLine();
                    InsuranceCategory inscatUpdate = new InsuranceCategory(idToUpdate, newName, newDescription);
                    inscats.updateCatIns(inscatUpdate);
                    break;
                case 3:
                    System.out.println("Enter category ID to delete:");
                    int idToDelete = scanner.nextInt();
                    scanner.nextLine();
                    inscats.deleteCatIns(idToDelete);
                    break;
                case 4:
                    inscats.getAll().forEach(System.out::println);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 5.");
            }
        }


        scanner.close();
    }
}