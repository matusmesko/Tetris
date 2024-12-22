import javax.swing.*;
import java.awt.*;

public class NextTetrominoPanel extends JPanel {

    private Tetromino nextTetromino;
    private Color[] colorTable;


    public NextTetrominoPanel() {
        setBackground(new Color(17, 124, 104));
        this.colorTable = new Color[]{
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

    public void setNextTetromino(Tetromino tetromino) {
        this.nextTetromino = tetromino;
        repaint(); // Repaint the panel to show the new Tetromino
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (nextTetromino != null) {
            drawTetromino(g, nextTetromino);
        }
    }

    private int blockWidth() {
        return (int) getSize().getWidth() / 10;
    }

    private int blockHeight() {
        return (int) getSize().getHeight() / 22;
    }


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

    }
}
