package tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.ToIntFunction;

public class Student {
    private final UUID id;
    private final String email;
    private final String name;
    private final List<Activity> activities;

    public Student(String email, String name) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public String getId() {
        return this.id.toString();
    }

    public String getEmail() {
        return this.email;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void printDetails() {
        System.out.printf("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n",
                this.id.toString(),
                Arrays.stream(this.getSubject(Subject.JAVA)).sum(),
                Arrays.stream(this.getSubject(Subject.DSA)).sum(),
                Arrays.stream(this.getSubject(Subject.DATABASES)).sum(),
                Arrays.stream(this.getSubject(Subject.SPRING)).sum()
        );
    }

    public boolean isEnrolled(Subject subject) {
        return this.getSubject(subject).length == 0;
    }

    public int[] getSubject(Subject subject) {
        ToIntFunction<Activity> getGrades = switch(subject) {
            case JAVA -> Activity::java;
            case DSA -> Activity::dsa;
            case DATABASES -> Activity::databases;
            case SPRING -> Activity::spring;
        };
        return this.activities.stream().mapToInt(getGrades).filter(grade -> grade != 0).toArray();
    }
}
