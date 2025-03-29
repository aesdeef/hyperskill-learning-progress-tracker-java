package tracker;

import java.util.UUID;

public class Student {
    private UUID id;
    private String email;
    private String name;
    private Grades grades;

    public Student(String email, String name) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.name = name;
        this.grades = new Grades();
    }

    public String getId() {
        return this.id.toString();
    }

    public String getEmail() {
        return this.email;
    }

    public void addPoints(int java, int dsa, int databases, int spring) {
        this.grades.java += java;
        this.grades.dsa += dsa;
        this.grades.databases += databases;
        this.grades.spring += spring;
    }

    public void printDetails() {
        System.out.printf("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n",
                this.id.toString(),
                this.grades.java,
                this.grades.dsa,
                this.grades.databases,
                this.grades.spring
        );
    }

    private static class Grades {
        Integer java = 0;
        Integer dsa = 0;
        Integer databases = 0;
        Integer spring = 0;
    }
}
