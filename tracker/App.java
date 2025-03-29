package tracker;

import java.util.*;

public class App {
    Scanner scanner = new Scanner(System.in);
    Map<String, Student> students = new LinkedHashMap<>();

    public void run() {
        System.out.println("Learning Progress Tracker");
        String command;
        while (true) {
            command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "" -> System.out.println("No input.");
                case "add students" -> this.addStudents();
                case "add points" -> this.addPoints();
                case "list" -> this.list();
                case "find" -> this.find();
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
            Student student = validateCredentials(credentials);
            if (student != null) {
                students.put(student.getEmail(), student);
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

    private void list() {
        if (students.isEmpty()) {
            System.out.println("No students found");
            return;
        }

        System.out.println("Students:");
        for (Student student : students.values()) {
            System.out.println(student.getId());
        }
    }

    private void addPoints() {
        System.out.println("Enter an id and points or 'back' to return");
        while (true) {
            String input = scanner.nextLine().trim();
            if ("back".equals(input)) {
                return;
            }
            String[] parts = input.split("\\s");
            if (parts.length != 5) {
                System.out.println("Incorrect points format");
                continue;
            }
            UUID id;
            try {
                id = UUID.fromString(parts[0]);
            } catch (Exception e) {
                System.out.println("No student is found for id=" + parts[0]);
                continue;
            }
            Student student = getStudentById(id);
            if (student == null) {
                System.out.println("No student is found for id=" + parts[0]);
                continue;
            }
            int[] grades = new int[4];
            try {
                for (int i = 0; i < 4; i++) {
                    int grade = Integer.parseInt(parts[i + 1]);
                    if (grade < 0) {
                        throw new Exception();
                    }
                    grades[i] = grade;
                }
            } catch (Exception e) {
                System.out.println("Incorrect points format");
                continue;
            }
            student.addPoints(grades[0], grades[1], grades[2], grades[3]);
            System.out.println("Points updated");
        }
    }

    private void find() {
        System.out.println("Enter an id or 'back' to return");
        while (true) {
            String input = scanner.nextLine().trim();
            if ("back".equals(input)) {
                return;
            }
            UUID id;
            try {
                id = UUID.fromString(input);
            } catch (Exception e) {
                System.out.println("No student is found for id=" + input);
                continue;
            }
            Student student = getStudentById(id);
            if (student == null) {
                System.out.println("No student is found for id=" + input);
                continue;
            }
            student.printDetails();
        }
    }

    private Student getStudentById(UUID id) {
        String stringId = id.toString();
        for (Student student : students.values()) {
            if (student.getId().equals(stringId)) {
                return student;
            }
        }
        return null;
    }

    private Student validateCredentials(String credentials) {
        if (credentials == null || credentials.isBlank()) {
            System.out.println("Incorrect credentials.");
            return null;
        }
        String[] parts = credentials.trim().split(" ");
        if (parts.length < 3) {
            System.out.println("Incorrect credentials.");
            return null;
        }

        String firstName = parts[0];
        String lastName = parts[parts.length - 2];
        String email = parts[parts.length - 1];

        // validate email
        if (!email.matches("[^@]+@[^@]+\\.[^@]+")) {
            System.out.println("Incorrect email");
            return null;
        }

        if (students.containsKey(email)) {
            System.out.println("This email is already taken.");
            return null;
        }

        // validate first and last name
        if (firstName.length() < 2
                // starts with a letter, ends with a letter, can have special characters in the middle
                || !firstName.matches("[A-Za-z][A-Za-z'\\-]*[A-Za-z]")
                || firstName.matches(".*['\\-]{2,}.*")  // no two special characters in a row
        ) {
            System.out.println("Incorrect first name");
            return null;
        }

        if (lastName.length() < 2
                // starts with a letter, ends with a letter, can have special characters in the middle
                || !lastName.matches("[A-Za-z][A-Za-z'\\-]*[A-Za-z]")
                || lastName.matches(".*['\\-]{2,}.*")  // no two special characters in a row
        ) {
            System.out.println("Incorrect last name");
            return null;
        }

        // validate remaining names
        for (int i = 1; i < parts.length - 2; i++) {
            String name = parts[i];
            if (!name.matches("[A-Za-z'\\-]+")  // has only the allowed characters
                    || !name.matches("^[A-Za-z].*")  // starts with a letter
                    || !name.matches(".*[A-Za-z]$")  // ends with a letter
            ) {
                System.out.println("Incorrect credentials.");
                return null;
            }
        }

        return new Student(email, String.join(" ", Arrays.copyOfRange(parts, 0, parts.length - 1)));
    }
}
