package matus;

import java.awt.*;

public class Utils {

    /**
     * Define colors of tetrominoes based on position in array.
     * @return Array of Colors where colors are initialized.
     */
    public static Color[] getColorTable() {
        return new Color[] {new Color(0, 0, 0), new Color(164, 135, 255), new Color(255, 128, 0), new Color(255, 0, 0), new Color(32, 128, 255), new Color(255, 0, 255), new Color(255, 255, 0), new Color(0, 255, 0)};
    }
}
