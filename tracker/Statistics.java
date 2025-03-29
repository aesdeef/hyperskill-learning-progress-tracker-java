package tracker;

import java.util.*;

public class Statistics {
    public static long countEnrolled(Collection<Student> students, Subject subject) {
        return students.stream().filter(s -> s.isEnrolled(subject)).count();
    }

    public static long countActivities(Collection<Student> students, Subject subject) {
        return students.stream().mapToInt(s -> s.getSubject(subject).length).sum();
    }

    public static double averageScore(Collection<Student> students, Subject subject) {
        long total = students.stream().mapToInt(s -> Arrays.stream(s.getSubject(subject)).sum()).sum();
        return (double) total / Statistics.countActivities(students, subject);
    }

    public static <T> String extremeSubjects(Map<Subject, T> values, T value) {
        EnumSet<Subject> extremes = EnumSet.noneOf(Subject.class);
        for (Subject s : Subject.values()) {
            if (values.get(s).equals(value)) {
                extremes.add(s);
            }
        }
        return String.join(", ", extremes.stream().map(Subject::toString).toList());
    }

    public static <T> void printOverall(Map<T, Student> students) {
        Map<Subject, Long> enrolmentCount = new HashMap<>();
        Map<Subject, Long> activityCount = new HashMap<>();
        Map<Subject, Double> averageScore = new HashMap<>();
        long minEnrolmentCount = Long.MAX_VALUE;
        long maxEnrolmentCount = Long.MIN_VALUE;
        long minActivityCount = Long.MAX_VALUE;
        long maxActivityCount = Long.MIN_VALUE;
        double minAverageScore = Double.MAX_VALUE;
        double maxAverageScore = Double.MIN_VALUE;

        boolean noActivities = true;

        for (Subject subject : Subject.values()) {
            long enrolment = Statistics.countEnrolled(students.values(), subject);
            long activity = Statistics.countActivities(students.values(), subject);
            double average = Statistics.averageScore(students.values(), subject);
            if (activity > 0) {
                noActivities = false;
            }
            enrolmentCount.put(subject, enrolment);
            activityCount.put(subject, activity);
            averageScore.put(subject, average);
            minEnrolmentCount = Long.min(minEnrolmentCount, enrolment);
            maxEnrolmentCount = Long.max(maxEnrolmentCount, enrolment);
            minActivityCount = Long.min(minActivityCount, activity);
            maxActivityCount = Long.max(maxActivityCount, activity);
            minAverageScore = Double.min(minAverageScore, average);
            maxAverageScore = Double.max(maxAverageScore, average);
        }

        final Long finalMinEnrolmentCount = minEnrolmentCount;
        final Long finalMaxEnrolmentCount = maxEnrolmentCount;
        final Long finalMinActivityCount = minActivityCount;
        final Long finalMaxActivityCount = maxActivityCount;
        final Double finalMinAverageScore = minAverageScore;
        final Double finalMaxAverageScore = maxAverageScore;

        if (noActivities) {
            System.out.println("Most popular: n/a");
            System.out.println("Least popular: n/a");
            System.out.println("Highest activity: n/a");
            System.out.println("Lowest activity: n/a");
            System.out.println("Easiest course: n/a");
            System.out.println("Hardest course: n/a");
        } else {
            String most = Statistics.extremeSubjects(enrolmentCount, finalMaxEnrolmentCount);
            String least = Statistics.extremeSubjects(enrolmentCount, finalMinEnrolmentCount);
            if (finalMaxEnrolmentCount.equals(finalMinEnrolmentCount)) {
                least = "n/a";
            }
            System.out.printf("Most popular: %s%n", most);
            System.out.printf("Least popular: %s%n", least);

            String highest = Statistics.extremeSubjects(activityCount, finalMaxActivityCount);
            String lowest = Statistics.extremeSubjects(activityCount, finalMinActivityCount);
            if (finalMinActivityCount.equals(finalMaxActivityCount)) {
                lowest = "n/a";
            }
            System.out.printf("Highest activity: %s%n", highest);
            System.out.printf("Lowest activity: %s%n", lowest);

            String easiest = Statistics.extremeSubjects(averageScore, finalMaxAverageScore);
            String hardest = Statistics.extremeSubjects(averageScore, finalMinAverageScore);
            if (finalMaxAverageScore.equals(finalMinAverageScore)) {
                hardest = "n/a";
            }
            System.out.printf("Easiest course: %s%n", easiest);
            System.out.printf("Hardest course: %s%n", hardest);
        }
    }
}
