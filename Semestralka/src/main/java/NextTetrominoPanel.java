import javax.swing.*;
import java.awt.*;

/**
 * Panel which displays next tetromino and basic controls
 *
 * @author Matúš Meško
 */
public class NextTetrominoPanel extends JPanel {

    private Tetromino nextTetromino;
    private Color[] colorTable;


    public NextTetrominoPanel() {
        setBackground(new Color(17, 124, 104));
        this.colorTable = Utils.getColorTable();
    }

    /**
     * Setting which tetromino we want to display as next tetromino
     * @param tetromino block to display
     */
    public void setNextTetromino(Tetromino tetromino) {
        this.nextTetromino = tetromino;
        repaint(); // Repaint the panel to show the new Tetromino
    }

    /**
     * Draw components in panel
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (nextTetromino != null) {
            drawTetromino(g, nextTetromino);
        }
    }

    /**
     * Get block width
     * @return block width in int
     */
    private int blockWidth() {
        return (int) getSize().getWidth() / 10;
    }

    /**
     * Get block height
     * @return block height in int
     */
    private int blockHeight() {
        return (int) getSize().getHeight() / 22;
    }

    /**
     * Draw all components (next tetromino and controls)
     * @param g Graphics which will be protected
     * @param tetromino current tetromino to display
     */
    private void drawTetromino(Graphics g, Tetromino tetromino) {
        Color curColor = colorTable[tetromino.getShape().ordinal()]; // Default color, you can customize this based on the Tetromino type
        g.setColor(curColor);


        // Calculate the starting position to center the Tetromino
        int startX = (getWidth() - (this.blockWidth() * 4)) / 2 + 70; // Center horizontally
        int startY = (getHeight() - (this.blockHeight() * 4)) / 2; // Center vertically

        for (int i = 0; i < 4; i++) {
            int x = tetromino.getX(i);
            int y = tetromino.getY(i);
            g.fillRect(startX + (x * this.blockWidth()), startY + (y * this.blockHeight()), this.blockWidth() - 2, this.blockHeight() - 2);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri", Font.BOLD, 25));
        g.drawString("Next Block", startX + this.blockWidth() - 75, startY + this.blockHeight() + 90);
        drawControls(g, 10, 550);
    }

    /**
     * Draw controls of the game
     * @param g Graphics which will be protected
     * @param x cords of strings
     * @param y cords of strings
     */
    private void drawControls(Graphics g, int x, int y) {
        g.drawString("W/\u2191 - Rotate Tetromino", x, y);
        g.drawString("A/\u2190 - Move Left", x, y + 30);
        g.drawString("D/\u2192 - Move Right", x, y + 60);
        g.drawString("S/\u2193 - Move Down", x, y + 90);
        g.drawString("Space - Move to bottom", x, y + 120);
        g.drawString("ESC/P - Pause", x, y + 150);
        g.drawString("R - Restart", x, y + 180);

    }
}
