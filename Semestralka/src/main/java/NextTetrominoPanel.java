import javax.swing.*;
import java.awt.*;

public class NextTetrominoPanel extends JPanel {

    private Tetromino nextTetromino;

    public NextTetrominoPanel() {
        setBackground(new Color(22, 163, 163));
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

//    private void drawTetromino(Graphics g, Tetromino tetromino) {
//        Color curColor = Color.WHITE; // Default color, you can customize this based on the Tetromino type
//        g.setColor(curColor);
//        int blockWidth = getWidth() / 4; // Assuming Tetromino has 4 blocks
//        int blockHeight = getHeight() / 4; // Adjust as necessary
//
//        for (int i = 0; i < 4; i++) {
//            int x = tetromino.getX(i);
//            int y = tetromino.getY(i);
//            g.fillRect(x * blockWidth + 10, (1 - y) * blockHeight + 10, blockWidth - 2, blockHeight - 2);
//        }
//    }

    private void drawTetromino(Graphics g, Tetromino tetromino) {
        Color curColor = Color.WHITE; // Default color, you can customize this based on the Tetromino type
        g.setColor(curColor);

        // Define smaller block size
        int blockWidth = getWidth() / 8; // Smaller blocks (8 blocks across)
        int blockHeight = getHeight() / 8; // Smaller blocks (8 blocks tall)

        // Calculate the starting position to center the Tetromino
        int startX = (getWidth() - (blockWidth * 4)) / 2; // Center horizontally
        int startY = (getHeight() - (blockHeight * 4)) / 2; // Center vertically

        for (int i = 0; i < 4; i++) {
            int x = tetromino.getX(i);
            int y = tetromino.getY(i);
            g.fillRect(startX + (x * blockWidth), startY + (y * blockHeight), blockWidth - 2, blockHeight - 2);
        }
    }
}
