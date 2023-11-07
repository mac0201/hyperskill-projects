package rockpaperscissors;

import java.util.Random;
class Computer {

    private final Random random;

    Computer() {
        this.random = new Random();
    }

    /**
     * Chooses a random shape from default shapes.
     * @return String name of shape
     * */
    String generateRandomShape() {
        DefaultShape[] shapes = DefaultShape.getAllDefaultShapeInstances();
        int index = random.nextInt(shapes.length);
        return shapes[index].getName();
    }

    int generateRandomIndex(int size) {
        return random.nextInt(size);
    }
}
