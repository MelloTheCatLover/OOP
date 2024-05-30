package ru.nsu.kozoliy.model.object;


public class Achievement {
    private String name;
    private String description;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }

    public static final Achievement EXCELLENT_PERFORMANCE = new Achievement("Excellent Performance", "Awarded for excellent performance in tasks.");
    public static final Achievement PERFECT_ATTENDANCE = new Achievement("Perfect Attendance", "Awarded for perfect attendance.");
    public static final Achievement TEAM_PLAYER = new Achievement("Team Player", "Awarded for outstanding teamwork.");
}
