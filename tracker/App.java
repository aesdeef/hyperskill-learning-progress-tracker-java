package tracker;

import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("Learning Progress Tracker");
        String command;
        while (true) {
            command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "" -> System.out.println("No input.");
                case "add students" -> this.addStudents();
                case "back" -> System.out.println("Enter 'exit' to exit the program.");
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Unknown command!");
            }
        }
    }

    private void addStudents() {
        System.out.println("Enter student credentials or 'back' to return:");
        int addedCount = 0;
        while (true) {
            String credentials = scanner.nextLine();
            if ("back".equals(credentials)) {
                break;
            }
            if (validateCredentials(credentials)) {
                System.out.println("The student has been added.");
                addedCount++;
            }
        }
        boolean plural = addedCount != 1;
        System.out.printf(
                "Total %d student%s %s been added.%n",
                addedCount,
                plural ? "s" : "",
                plural ? "have" : "has"
        );
    }

    private boolean validateCredentials(String credentials) {
        if (credentials == null || credentials.isBlank()) {
            System.out.println("Incorrect credentials.");
            return false;
        }
        String[] parts = credentials.trim().split(" ");
        if (parts.length < 3) {
            System.out.println("Incorrect credentials.");
            return false;
        }

        String firstName = parts[0];
        String lastName = parts[parts.length - 2];
        String email = parts[parts.length - 1];

        // validate email
        if (!email.matches("[^@]+@[^@]+\\.[^@]+")) {
            System.out.println("Incorrect email");
            return false;
        }

        // validate first and last name
        if (firstName.length() < 2
                // starts with a letter, ends with a letter, can have special characters in the middle
                || !firstName.matches("[A-Za-z][A-Za-z'\\-]*[A-Za-z]")
                || firstName.matches(".*['\\-]{2,}.*")  // no two special characters in a row
        ) {
            System.out.println("Incorrect first name");
            return false;
        }

       if (lastName.length() < 2
                // starts with a letter, ends with a letter, can have special characters in the middle
                || !lastName.matches("[A-Za-z][A-Za-z'\\-]*[A-Za-z]")
                || lastName.matches(".*['\\-]{2,}.*")  // no two special characters in a row
        ) {
            System.out.println("Incorrect last name");
            return false;
        }

        // validate remaining names
        for (int i = 1; i < parts.length - 2; i++) {
            String name = parts[i];
            if (!name.matches("[A-Za-z'\\-]+")  // has only the allowed characters
                    || !name.matches("^[A-Za-z].*")  // starts with a letter
                    || !name.matches(".*[A-Za-z]$")  // ends with a letter
            ) {
                System.out.println("Incorrect credentials.");
                return false;
            }
        }

        return true;
    }
}
