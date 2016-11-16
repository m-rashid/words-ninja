import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;
/**
 * This object type is a subtype of JButton.
 */
public class TileGUI extends JButton {

    private Tile model;

    /**
     * Create a TileGUI object that provides a graphical representation for the given Tile.
     */
    public TileGUI(Tile tile) {
        super();
        model = tile;
        setText(makeHTML(super.getForeground(), tile));
        this.setContentAreaFilled(true);
    }

    private static String setTwoDigits(String string) {
        if (string.length()==1) {
            return "0"+string;
        }
        else if (string.length()==2) {
            return string;
        }
        else {
            throw new IllegalArgumentException("TileGUI:setTwoDigits("+string+"): more than two digits");
        }
    }

    private static String makeHTML(Color colour, Tile tile) {

        String redString = Integer.toHexString(colour.getRed());
        String greenString = Integer.toHexString(colour.getGreen());
        String blueString = Integer.toHexString(colour.getBlue());
        redString = setTwoDigits(redString);
        greenString = setTwoDigits(greenString);
        blueString = setTwoDigits(blueString);

        StringBuffer result = new StringBuffer();
        result.append("<html><font color=\"#");
        result.append(redString);
        result.append(greenString);
        result.append(blueString);
        result.append("\" size=\"+5\">"+tile.letter()+"</font><sub>"+tile.value()+"</sub>");
        return result.toString();
    }

    /**
     * Set the foreground colour for this graphical component. 
     */
    public void setForeground(Color colour) {
        super.setForeground(colour);
        if (model!=null) { 
            this.setText(makeHTML(colour, model));
        }
    }

    /**
     * Obtain the Tile object graphically represented by this TileGUI object.
     */
    public Tile getTile() { return model; }

}
