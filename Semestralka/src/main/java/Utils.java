import java.awt.*;

/**
 * Utils class
 *
 * @author Matúš Meško
 */
public class Utils {

    /**
     * Color table of tetrominoes
     * @return Array of Colors where are init colors
     */
    public static Color[] getColorTable() {
        return new Color[]{
                new Color(0, 0, 0),
                new Color(164, 135, 255),
                new Color(255, 128, 0),
                new Color(255, 0, 0),
                new Color(32, 128, 255),
                new Color(255, 0, 255),
                new Color(255, 255, 0),
                new Color(0, 255, 0)
        };
    }
}
