package tracker;

public enum Subject {
    JAVA,
    DSA,
    DATABASES,
    SPRING;

    @Override
    public String toString() {
        return switch (this) {
            case JAVA -> "Java";
            case DSA -> "DSA";
            case DATABASES -> "Databases";
            case SPRING -> "Spring";
        };
    }

    public int target() {
        return switch (this) {
            case JAVA -> 600;
            case DSA -> 400;
            case DATABASES -> 480;
            case SPRING -> 550;
        };
    }
}
