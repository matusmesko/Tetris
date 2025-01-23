package matus.panels;

import matus.Tetromino;
import matus.Utils;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class NextTetrominoPanel extends JPanel {

    private Tetromino nextTetromino;
    private Color[] colorTable;


    /**
     * Constructor the matus.panels.NextTetrominoPanel and initializes its properties.
     * Sets the background color and retrieves the color table for tetrominoes.
     */
    public NextTetrominoPanel() {
        setBackground(new Color(0x295075));
        this.colorTable = Utils.getColorTable();
    }

    /**
     * Sets the next tetromino to be displayed in the panel.
     * This method updates the next tetromino and triggers a repaint
     * of the panel to show the new tetromino.
     *
     * @param tetromino the matus.Tetromino to be set as the next tetromino.
     */
    public void setNextTetromino(Tetromino tetromino) {
        this.nextTetromino = tetromino;
        repaint(); // Repaint the panel to show the new matus.Tetromino
    }

    /**
     * Paints the component by calling the superclass's paintComponent method
     * and drawing the next tetromino if it is not null.
     *
     * @param g the Graphics context used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.nextTetromino != null) {
            this.drawTetromino(g, this.nextTetromino);
        }
    }

    /**
     * Calculates the width of a block based on the size of the component.
     * The width is determined by dividing the total width of the component
     * by a fixed value (10).
     *
     * @return the width of a single block in pixels.
     */
    private int blockWidth() {
        return (int)getSize().getWidth() / 10;
    }

    /**
     * Calculates the height of a block based on the size of the component.
     * The height is determined by dividing the total height of the component
     * by a fixed value (22).
     *
     * @return the height of a single block in pixels.
     */
    private int blockHeight() {
        return (int)getSize().getHeight() / 22;
    }

    /**
     * Draws the specified tetromino on the Graphics context.
     * The tetromino is drawn in its corresponding color and centered in the panel.
     * Additionally, it draws the label "Next Block" and the control instructions.
     *
     * @param g the Graphics context used for drawing.
     * @param tetromino the matus.Tetromino to be drawn.
     */
    private void drawTetromino(Graphics g, Tetromino tetromino) {
        Color curColor = this.colorTable[tetromino.getShape().ordinal()]; // Default color, you can customize this based on the matus.Tetromino type
        g.setColor(curColor);

        Dimension size = getSize();
        FontMetrics fm = g.getFontMetrics();
        String text = "Next Block";
        int textX = (size.width - fm.stringWidth(text)) / 2; //center
        int textY = size.height / 2; //center
        int startX = size.width / 2; // Center horizontally
        int startY = (size.height / 2) - 70; // Center vertically
        for (int i = 0; i < 4; i++) {
            int x = tetromino.getX(i);
            int y = tetromino.getY(i);
            g.fillRect(startX + (x * this.blockWidth()), startY + (y * this.blockHeight()), this.blockWidth() - 2, this.blockHeight() - 2);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri", Font.BOLD, 25));
        g.drawString(text, textX, textY + 50);
        this.drawControls(g, 10, 550);
    }

    /**
     * Draws the control instructions for the game on the specified Graphics context.
     * The instructions include controls for rotating, moving, dropping the tetromino,
     * pausing the game, and restarting.
     *
     * @param g the Graphics context used for drawing.
     * @param x the x-coordinate for the starting position of the control instructions.
     * @param y the y-coordinate for the starting position of the control instructions.
     */
    private void drawControls(Graphics g, int x, int y) {
        g.drawString("W/\u2191 - Rotate matus.Tetromino", x, y);
        g.drawString("A/\u2190 - Move Left", x, y + 30);
        g.drawString("D/\u2192 - Move Right", x, y + 60);
        g.drawString("S/\u2193 - Move Down", x, y + 90);
        g.drawString("Space - Move to bottom", x, y + 120);
        g.drawString("ESC/P - Pause", x, y + 150);
        g.drawString("R - Restart", x, y + 180);

    }
}
