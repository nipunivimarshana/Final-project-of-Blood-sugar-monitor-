/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication16;

/**
 *
 * @author HMNV HERATH
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.Year;


public class BloodSugarTracker {

    private static final int MAX_USERS = 5; // Maximum number of users allowed
    private Map<String, UserData> userDatabase;

    public BloodSugarTracker() {
        this.userDatabase = new HashMap<>();
    }

    public static void main(String[] args) {
        BloodSugarTracker tracker = new BloodSugarTracker();
        tracker.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "1":
                    createRecord(scanner);
                    break;
                case "2":
                    showAllUserData();
                    break;
                case "3":
                    showUserData(scanner);
                    break;
                case "4":
                    deleteAllRecords();
                    break;
                case "5":
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Create a record");
        System.out.println("2. Show blood sugar data for all users");
        System.out.println("3. Show blood sugar data for a selected user");
        System.out.println("4. Delete all records");
        System.out.println("5. Exit application");
    }

    private void createRecord(Scanner scanner) {
        if (userDatabase.size() >= MAX_USERS) {
            System.out.println("Cannot add more users. Maximum limit reached.");
            return;
        }

        System.out.print("Enter user id: ");
        String userId = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter year of birth: ");
        int yearOfBirth = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter blood sugar level before meals (fasting): ");
        double fastingBloodSugar = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter blood sugar level after meals (post-prandial): ");
        double postPrandialBloodSugar = Double.parseDouble(scanner.nextLine());

        UserData userData = new UserData(name, yearOfBirth, fastingBloodSugar, postPrandialBloodSugar);
        userDatabase.put(userId, userData);

        System.out.println("Record created successfully.");
    }

    private void showAllUserData() {
        System.out.println("\nBlood Sugar Data for All Users:");
        for (UserData userData : userDatabase.values()) {
            userData.display();
            userData.checkDiabetes();
        }
    }

    private void showUserData(Scanner scanner) {
        System.out.print("Enter user id: ");
        String userId = scanner.nextLine();

        if (userDatabase.containsKey(userId)) {
            userDatabase.get(userId).display();
            userDatabase.get(userId).checkDiabetes();
        } else {
            System.out.println("User ID not found.");
        }
    }

    private void deleteAllRecords() {
        userDatabase.clear();
        System.out.println("All records deleted successfully.");
    }

    private void exit() {
        System.out.println("Exiting application. Goodbye!");
        System.exit(0);
    }

    private static class UserData {
        private String name;
        private int yearOfBirth;
        private double fastingBloodSugar;
        private double postPrandialBloodSugar;

        public UserData(String name, int yearOfBirth, double fastingBloodSugar, double postPrandialBloodSugar) {
            this.name = name;
            this.yearOfBirth = yearOfBirth;
            this.fastingBloodSugar = fastingBloodSugar;
            this.postPrandialBloodSugar = postPrandialBloodSugar;
        }

        public int calculateAge() {
            int currentYear = Year.now().getValue();
            return currentYear - yearOfBirth;
        }
        
        public void display() {
            int age = calculateAge();
            System.out.println(
            "Name: " + name + ", Age: " + age + ", Fasting Blood Sugar: " + fastingBloodSugar +
                    ", Post-Prandial Blood Sugar: " + postPrandialBloodSugar );     
        }
        
        public void checkDiabetes() {
            System.out.print("Diabetes Status: ");
            if (fastingBloodSugar <= 99 && postPrandialBloodSugar <= 140) {
                System.out.println("No diabetes");
            } else if (fastingBloodSugar >= 80 && fastingBloodSugar <= 130 && postPrandialBloodSugar <= 180) {
                System.out.println("Adults with Type 1 or Type 2 diabetes");
            } else if (fastingBloodSugar >= 90 && fastingBloodSugar <= 130 && postPrandialBloodSugar <= 150) {
                System.out.println("Children with Type 1 diabetes");
            } else if (yearOfBirth >= 65 && fastingBloodSugar >= 80 && fastingBloodSugar <= 180 &&
                    postPrandialBloodSugar <= 200) {
                System.out.println("65 or older with diabetes");
            } else if (fastingBloodSugar <= 95 && postPrandialBloodSugar <= 140) {
                System.out.println("Pregnant people with T1D or gestational diabetes");
            } else {
                System.out.println("No diabetes");
            }
        }
    }
}
