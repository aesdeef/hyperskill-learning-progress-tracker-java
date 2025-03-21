package tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Learning Progress Tracker");
        String command;
        while (true) {
            command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "" -> System.out.println("No input.");
                case "exit" -> {
                    System.out.println("Bye!");
                    System.exit(0);
                }
                default -> System.out.println("Unknown command!");
            }
        }
    }
}
