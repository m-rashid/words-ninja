/**
 * A Tile object represents a scrabble tile. It has two attributes, the letter and the value.
 */
public class Tile {

    private char letter;
    private int value;

    /** 
     * Create a Tile for the given letter and value.
     */    
    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }

    public char letter() { return letter;}
    
    public int value() { return value;}

    public String toString() {
        return "("+letter+", "+value+")";
    }
}
