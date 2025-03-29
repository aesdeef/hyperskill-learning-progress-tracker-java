package tracker;

public enum Subject {
    JAVA,
    DSA,
    DATABASES,
    SPRING;

    @Override
    public String toString() {
        return switch(this) {
            case JAVA -> "Java";
            case DSA -> "DSA";
            case DATABASES -> "Databases";
            case SPRING -> "Spring";
        };
    }
}
