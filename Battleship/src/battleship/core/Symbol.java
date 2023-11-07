package battleship.core;

public enum Symbol {
    FOG('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M');

    private final char SYMBOL;

    Symbol(char symbol) {
        this.SYMBOL = symbol;
    }

    public char getSymbol() {
        return this.SYMBOL;
    }
}
