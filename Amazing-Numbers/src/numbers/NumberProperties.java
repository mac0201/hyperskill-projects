package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum NumberProperties {
    EVEN("even", "odd"),
    ODD("odd", "even"),
    DUCK("duck", "spy"),
    SPY("spy", "duck"),
    SQUARE("square", "sunny"),
    SUNNY("sunny", "square"),
    HAPPY("happy", "sad"),
    SAD("sad", "happy"),
    JUMPING("jumping", null),
    BUZZ("buzz", null),
    PALINDROMIC("palindromic", null),
    GAPFUL("gapful", null);

    private final String name;
    private final String exclusive;

    NumberProperties(String name, String exclusive) {
        this.name = name;
        this.exclusive = exclusive;
    }

    /**
     * Get exclusive of current NumberProperties instance
     * @return exclusive's name
     * */
    private String getExclusive() { return this.exclusive; };

    // ArrayList
    public static List<String> getAllAvailablePropertyNames() {
        ArrayList<String> list = new ArrayList<>();
        Arrays.stream(NumberProperties.values()).toList().forEach(property -> list.add(property.name) );
        return list;
    }

    public static void printAllAvailableProperties() {
        System.out.print("Available properties: [" +
                    String.join(", ", getAllAvailablePropertyNames()).toUpperCase() +
                    "]\n\n");
    }

    /**
     * Checks if all provided properties are valid
     *
     * @param targetProperties a list of target properties
     * @return true if all properties are valid, else false
     * */
    public static boolean validProperties(List<String> targetProperties) {
        // holds invalid properties from arg
        ArrayList<String> invalidProperties = new ArrayList<>();

        // loop through targetProperties, if property does not exist add to invalidProperties
        for (String property : targetProperties) {
            String temp = property;
            // if target begins with '-'
            if (property.charAt(0) == '-') {
                temp = property.substring(1);
            }

            if (!getAllAvailablePropertyNames().contains(temp)) {
                invalidProperties.add(property);
            }
        }

        // print invalid properties if list is not empty and return false
        if (!invalidProperties.isEmpty()) {
            if (invalidProperties.size() == 1) {
                System.out.printf("The property [%S] is wrong.\n", invalidProperties.get(0));
            }
            else {
                System.out.printf("The properties %S are wrong.\n", invalidProperties);
            }
            printAllAvailableProperties();
            return false;
        }
        return true;
    }

    /**
     * Checks if a list of target properties contains mutually exclusive properties and returns a boolean.
     * This function also prints the invalid properties to console, if any.
     *
     * @param targetProperties a list of properties
     * @return true if list contains mutually exclusive properties, else false
     * */
    public static boolean mutuallyExclusiveProperties(List<String> targetProperties) {
        ArrayList<String> incorrect = new ArrayList<>();
        NumberProperties propertyInstance;

        for (String propertyName : targetProperties) {

            String temp = propertyName.replace("-", "");

            // check for direct opposite. at the moment only checks for first exclusives
            if (targetProperties.contains(temp) && targetProperties.contains("-" + temp)) {
                incorrect.add(temp);
            }

            // We know the property is valid, so get correct enum instance based on the provided name
            // (remove the '-' from start to avoid error, and convert the name to uppercase)
            propertyInstance = NumberProperties.valueOf(propertyName.replace("-", "").toUpperCase());

            if (propertyName.charAt(0) == '-') {
                if (propertyName.equals("-even") || propertyName.equals("-odd")) {
                    if (targetProperties.contains(propertyName) && targetProperties.contains("-" + propertyInstance.getExclusive())) {
                        incorrect.add(propertyName);
                    }
                }
            }
            else {
                if (targetProperties.contains(propertyInstance.getExclusive())) {
                    incorrect.add(propertyName);
                }
            }
        }

        if (incorrect.isEmpty()) {
            return false;
        }

        System.out.printf("The request contains mutually exclusive properties: %S\n", incorrect);
        System.out.println("There are no numbers with these properties.\n");
        return true;
    }
}