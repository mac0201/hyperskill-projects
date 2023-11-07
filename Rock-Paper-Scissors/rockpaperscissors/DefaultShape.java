package rockpaperscissors;

import java.util.ArrayList;

enum DefaultShape {
    ROCK("rock", "paper"),
    PAPER("paper", "scissors"),
    SCISSORS("scissors", "rock");

    private final String name;
    private final String counter;

    DefaultShape(String name, String counter) {
        this.name = name;
        this.counter = counter;
    }

    public static String getCounter(String shape) {
        for (DefaultShape s : DefaultShape.values()) {
            if (s.name.equals(shape)) {
                return s.counter;
            }
        }
        return null;
    }

    public static boolean isValidDefaultShape(String targetShape) {
        for (DefaultShape shape : DefaultShape.values()) {
            if (shape.name.equals(targetShape)) {
                return true;
            }
        }
        return false;
    }

    public static DefaultShape[] getAllDefaultShapeInstances() {
        return DefaultShape.values();
    }

    public static ArrayList<String> getAllDefaultShapeNames() {
        ArrayList<String> names = new ArrayList<>(DefaultShape.values().length);
        for (DefaultShape shape : DefaultShape.values()) {
            names.add(shape.getName());
        }
        return names;
    }

    String getName() {
        return this.name;
    }
}
