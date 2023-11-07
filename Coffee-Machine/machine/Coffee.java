package machine;

public enum Coffee {
    ESPRESSO("espresso", 4,250, 0, 16),
    LATTE("latte", 7, 350, 75, 20),
    CAPPUCCINO("cappuccino", 6, 200, 100, 12);

    private final String name;
    private final int cost;
    private final int waterRequired;
    private final int milkRequired;
    private final int coffeeRequired;

    Coffee(String name, int cost, int waterRequired, int milkRequired, int coffeeRequired) {
        this.name = name;
        this.cost = cost;
        this.waterRequired = waterRequired;
        this.milkRequired = milkRequired;
        this.coffeeRequired = coffeeRequired;
    }

    public static int getInstanceCount() {
        return Coffee.values().length;
    }

    public static Coffee getCoffeeInstanceByOrdinal(int ordinal) {
        for (Coffee coffee : Coffee.values()) {
            if (coffee.ordinal() == ordinal) {
                return coffee;
            }
        }
        return null;
    }

    public static String[] getAllNames() {
        int totalInstances = Coffee.getInstanceCount();
        String[] names = new String[totalInstances];
        for (int i = 0; i < totalInstances; i++) {
            names[i] = Coffee.values()[i].getName();
        }
        return names;
    }

    public int getWaterRequired() { return waterRequired; }

    public int getMilkRequired() { return milkRequired; }

    public int getCoffeeRequired() { return coffeeRequired; }

    public int getCost() { return cost; }

    public String getName() { return name; }
}
