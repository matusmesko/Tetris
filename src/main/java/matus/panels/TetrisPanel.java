package matus.panels;

import matus.MusicController;
import matus.Tetromino;
import matus.TetrominoType;
import matus.Utils;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingConstants;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TetrisPanel extends JPanel implements ActionListener {
    private final int boardwidth = 10; // game board x size
    private final int boardheight = 22; // game board y size

    // game status & timer
    private Timer timer;
    private boolean isStillFalling = false;
    private boolean gameStarted = false;
    private boolean gamePaused = false;
    private int currentScore = 0; // removed lines == score

    // position of current block
    private int blockX = 0;
    private int blockY = 0;

    // current tetromino
    private Tetromino currentTetromino;

    // next tetromino
    private Tetromino nextTetromino;
    private ArrayList<Tetromino> tetrominoQueue;

    // logical game block
    private TetrominoType[] gameField;
    private Color[] colorTable;

    // adjusting game status
    private String currentStatus;
    private String currentLevel;
    private int currentTimerDelay;


    private MusicController backgroundMusic;
    private MusicController miscMusic;

    private NextTetrominoPanel nextTetrominoPanel;
    private JFrame mainFrame;

    /**
     * Constructor GameBoardPanel for the Tetris game.
     * This constructor initializes the game board, sets up the music controllers,
     * initializes the tetromino queue, and starts the game timer. It also sets up
     * the key listener for handling user input and initializes the game board.
     *
     * @param timerDelay the delay for the game timer in milliseconds.
     * @param mainMusic the matus.MusicController for the mainMusic background music.
     * @param miscMusic the matus.MusicController for miscellaneous sound effects.
     * @param nextTetrominoPanel the panel that displays the next tetromino.
     * @param mainFrame the mainMusic JFrame that contains the game.
     */
    public TetrisPanel(int timerDelay, MusicController mainMusic, MusicController miscMusic, NextTetrominoPanel nextTetrominoPanel, JFrame mainFrame) {
        setFocusable(true);
        setBackground(new Color(0x1A3F61));
        this.mainFrame = mainFrame;
        this.tetrominoQueue = new ArrayList<>();
        this.nextTetrominoPanel = nextTetrominoPanel;
        this.currentTetromino = new Tetromino();
        this.nextTetromino = new Tetromino();
        this.timer = new Timer(timerDelay, this);
        this.timer.start(); // activate timer
        this.currentTimerDelay = timerDelay;
        this.backgroundMusic = mainMusic;
        this.miscMusic = miscMusic;

        this.gameField = new TetrominoType[this.boardwidth * this.boardheight];
        this.colorTable = Utils.getColorTable();



        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                TetrisPanel.this.initKeyEvent(e);
            }
        });

        this.initBoard();
    }

    /**
     * Initializes key event handling for the game.
     * This method processes key events to control the current tetromino,
     * including movement, rotation, dropping, and pausing the game.
     *
     * @param e the KeyEvent containing information about the key pressed.
     */
    private void initKeyEvent(KeyEvent e) {
        if (!this.gameStarted || this.currentTetromino.getShape() == TetrominoType.NO_BLOCK) {
            return;
        }

        int keycode = e.getKeyCode();

        if (keycode == 'p' || keycode == 'P' || keycode == KeyEvent.VK_ESCAPE) {
            this.pause();
            return;
        }
        if (this.gamePaused) {
            return;
        }

        switch (keycode) {
            case 'a':
            case 'A':
            case KeyEvent.VK_LEFT:
                //canMove(currentTetromino, blockX - 1, blockY);
                this.isValidPosition(this.currentTetromino, this.blockX - 1, this.blockY, true);
                break;
            case 'd':
            case 'D':
            case KeyEvent.VK_RIGHT:
                //canMove(currentTetromino, blockX + 1, blockY);
                this.isValidPosition(this.currentTetromino, this.blockX + 1, this.blockY, true);
                break;
            case 'w':
            case 'W':
            case KeyEvent.VK_UP:
                //canMove(currentTetromino.rotateRight(), blockX, blockY);
                this.isValidPosition(this.currentTetromino.rotateRight(), this.blockX, this.blockY, true);
                break;
            case 's':
            case 'S':
            case KeyEvent.VK_DOWN:
                this.moveTetrominoDown();
                break;
            case KeyEvent.VK_SPACE:
                this.dropTetrominoDown();
                break;
            case 'p':
            case KeyEvent.VK_ESCAPE:
            case 'P':
                this.pause();
                break;
            case 'r':
            case 'R':
                this.gameOver();
                break;
        }
    }

    /**
     * Sets the timer delay based on the current score.
     * The delay decreases as the score increases, making the game more challenging.
     */
    private void setTimer() {
        int level = this.currentScore / 10;
        this.currentTimerDelay = Math.max(100, 500 - level * 40);
        this.timer.setDelay(this.currentTimerDelay);
    }

    /**
     * Initializes the game board by setting all positions to NO_BLOCK.
     * This method is called at the start of the game to prepare the board for play.
     */
    private void initBoard() {
        for (int i = 0; i < this.boardwidth * this.boardheight; i++) {
            this.gameField[i] = TetrominoType.NO_BLOCK;
        }
    }

    /**
     * Timer callback method that is called at regular intervals.
     * This method handles the falling of the current tetromino and
     * generates a new tetromino when the current one has landed.
     *
     * @param e the ActionEvent triggered by the timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isStillFalling) {
            this.isStillFalling = false;
            this.newTetromino();
        } else {
            this.moveTetrominoDown();
        }
    }

    /**
     * Starts the game by initializing the game state, resetting the score,
     * and starting the timer. If the game is paused, it will not start.
     */
    public void start() {
        if (this.gamePaused) {
            return;
        }

        this.gameStarted = true;
        this.isStillFalling = false;
        this.currentScore = 0;
        this.initBoard();

        this.newTetromino();
        this.timer.start();
        try {
            this.backgroundMusic.playMusicLoop("/main.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Pauses or resumes the game based on the current state.
     * If the game is paused, the timer stops and the background music is paused.
     * If the game is resumed, the timer starts and the music resumes.
     */
    public void pause() {
        if (!this.gameStarted) {
            return;
        }
        this.gamePaused = !this.gamePaused;
        if (this.gamePaused) {
            this.timer.stop();
            this.backgroundMusic.pauseMusic();
        } else {
            this.timer.start();
            this.backgroundMusic.resumeMusic();
        }

        repaint();
    }


    /**
     * Calculates the actual width of a block (tetromino) on the screen.
     * The width is determined by dividing the total width of the component
     * by the predefined constant board width.
     *
     * @return the width of a single block in pixels.
     */
    private int blockWidth() {
        return (int)this.getSize().getWidth() / this.boardwidth;
    }

    /**
     * Calculates the actual height of a block (tetromino) on the screen.
     * The height is determined by dividing the total height of the component
     * by the predefined constant board height.
     *
     * @return the height of a single block in pixels.
     */
    private int blockHeight() {
        return (int)this.getSize().getHeight() / this.boardheight;
    }

    /**
     * Retrieves the current tetromino type at the specified position in the game field.
     * The position is specified by the x and y coordinates, which are used to calculate
     * the index in the game field array.
     *
     * @param x the x-coordinate of the tetromino position.
     * @param y the y-coordinate of the tetromino position.
     * @return the matus.TetrominoType at the specified (x, y) position in the game field.
     */
    private TetrominoType curTetrominoPos(int x, int y) {
        return this.gameField[(y * this.boardwidth) + x];
    }

    /**
     * Paints the current state of the game on the screen.
     * This method is called whenever the component needs to be redrawn.
     * It updates the score and level display, and draws the game board,
     * the current tetromino, and its shadow if the game is not paused.
     *
     * @param g the Graphics context used for drawing.
     */
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        this.currentStatus =  "Score: " + this.currentScore;
        this.currentLevel = "Level: " + (this.currentScore / 10 + 1);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri", Font.BOLD, 28));
        g.drawString(this.currentStatus, 15, 35);
        g.drawString(this.currentLevel, 15, 70);
        Dimension size = getSize();
        int boardTop = (int)size.getHeight() - this.boardheight * this.blockHeight();

        //draw PAUSED text when game is paused
        if (this.gamePaused) {
            g.setFont(new Font("Calibri", Font.BOLD, 48));
            String pausedText = "PAUSED";
            FontMetrics fm = g.getFontMetrics();
            int x = (size.width - fm.stringWidth(pausedText)) / 2; //center
            int y = size.height / 2; //center
            g.drawString(pausedText, x, y);
        }

        this.drawElements(g, boardTop);
    }

    /**
     * Draws the elements of the game, including the tetromino shadow,
     * the game board, and the current tetromino.
     *
     * @param g the Graphics context used for drawing.
     * @param boardTop the top position of the game board on the screen.
     */
    private void drawElements(Graphics g, int boardTop) {
        this.drawTetrominoShadow(g, boardTop);
        this.drawGameBoard(g, boardTop);
        this.drawCurrentTetromino(g, boardTop);
    }


    /**
     * Draws the shadow of the current tetromino on the game board.
     * The shadow indicates the lowest valid position the tetromino can fall to.
     *
     * @param g the Graphics context used for drawing.
     * @param boardTop the top position of the game board on the screen.
     */
    private void drawTetrominoShadow(Graphics g, int boardTop) {
        int shadowY = this.blockY;

        // Find the lowest position for the tetromino
        while (shadowY > 0) {
            if (!this.isValidPosition(this.currentTetromino, this.blockX, shadowY - 1, false)) {
                break;
            }
            shadowY--;
        }

        // Draw the shadow at the calculated position
        for (int i = 0; i < 4; i++) {
            int x = this.blockX + this.currentTetromino.getX(i);
            int y = shadowY - this.currentTetromino.getY(i);
            this.drawRectangle(g, x * this.blockWidth(), boardTop + (this.boardheight - y - 1) * this.blockHeight(),
                    this.currentTetromino.getShape(), true);
        }
    }

    /**
     * Draws the game board, rendering all the tetrominoes that are currently
     * fixed in place.
     *
     * @param g the Graphics context used for drawing.
     * @param boardTop the top position of the game board on the screen.
     */
    private void drawGameBoard(Graphics g, int boardTop) {
        for (int row = 0; row < this.boardheight; row++) {
            for (int col = 0; col < this.boardwidth; col++) {
                TetrominoType shape = this.curTetrominoPos(col, this.boardheight - row - 1);
                if (shape != TetrominoType.NO_BLOCK) {
                    this.drawRectangle(g, col * this.blockWidth(), boardTop + row * this.blockHeight(), shape, false);
                }
            }
        }
    }

    /**
     * Draws the current tetromino on the game board.
     * If the current tetromino is of type NO_BLOCK, it does not draw anything.
     *
     * @param g the Graphics context used for drawing.
     * @param boardTop the top position of the game board on the screen.
     */
    private void drawCurrentTetromino(Graphics g, int boardTop) {
        if (this.currentTetromino.getShape() == TetrominoType.NO_BLOCK) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            int x = this.blockX + this.currentTetromino.getX(i);
            int y = this.blockY - this.currentTetromino.getY(i);
            this.drawRectangle(g, x * this.blockWidth(), boardTop + (this.boardheight - y - 1) * this.blockHeight(),
                    this.currentTetromino.getShape(), false);
        }
    }

    /**
     * Draws a tetromino at the specified position on the game board.
     * The tetromino can be drawn as a shadow or as a regular block.
     *
     * @param g the Graphics context used for drawing.
     * @param x the x-coordinate where the tetromino should be drawn.
     * @param y the y-coordinate where the tetromino should be drawn.
     * @param type the type of the tetromino to be drawn.
     * @param isShadow a boolean indicating whether the tetromino is a shadow.
     * If true, the tetromino is drawn in a darker color to indicate its shadow.
     */
    private void drawRectangle(Graphics g, int x, int y, TetrominoType type, boolean isShadow) {
        Color curColor = this.colorTable[type.ordinal()];

        if (!isShadow) {
            g.setColor(curColor);
            g.fillRect(x + 1, y + 1, this.blockWidth() - 2, this.blockHeight() - 2);
        } else {
            g.setColor(curColor.darker().darker());
            g.fillRect(x + 1, y + 1, this.blockWidth() - 2, this.blockHeight() - 2);
        }
    }


    /**
     * Clears any full lines from the game board.
     * This method checks each line from the bottom of the board to the top,
     * counting how many lines are full. For each full line, it clears the line,
     * plays a sound effect, and updates the score. If any lines are cleared,
     * it sets the current tetromino to NO_BLOCK and updates the timer.
     */
    private void clearFullLine() {
        int fullLines = 0;

        for (int i = this.boardheight - 1; i >= 0; i--) {
            if (this.isLineFull(i)) {
                fullLines++;
                this.clearLine(i);
                this.playSound("/on.wav");
            }
        }

        if (fullLines > 0) {
            this.currentScore += fullLines;
            this.isStillFalling = true;
            this.currentTetromino.setShape(TetrominoType.NO_BLOCK);
            this.setTimer();
            repaint();
        }

        this.playSound("/off.wav");
    }

    /**
     * Checks if a specific line is full.
     * A line is considered full if there are no NO_BLOCK positions in that line.
     *
     * @param row the index of the row to check.
     * @return true if the line is full, false otherwise.
     */
    private boolean isLineFull(int row) {
        for (int col = 0; col < this.boardwidth; col++) {
            if (this.curTetrominoPos(col, row) == TetrominoType.NO_BLOCK) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clears a specific line by shifting all lines above it down by one.
     * This method effectively removes the specified line and updates the game field.
     *
     * @param row the index of the row to clear.
     */
    private void clearLine(int row) {
        for (int r = row; r < this.boardheight - 1; r++) {
            System.arraycopy(this.gameField, (r + 1) * this.boardwidth, this.gameField, r * this.boardwidth, this.boardwidth);
        }
    }

    /**
     * Plays a sound effect from the specified sound path.
     * This method attempts to play the sound and catches any exceptions that may occur,
     * printing the stack trace if an error happens.
     *
     * @param soundPath the path to the sound file to be played.
     */
    private void playSound(String soundPath) {
        try {
            this.miscMusic.playMusic(soundPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the specified tetromino can be placed at the given coordinates.
     * This method verifies that the tetromino's position is within the boundaries of the board
     * and that it does not overlap with any already occupied positions. If the position is valid
     * and the updatedPosition flag is true, it updates the current tetromino's position and repaints
     * the game board.
     *
     * @param tetromino the matus.Tetromino to check for validity.
     * @param x the x-coordinate to check.
     * @param y the y-coordinate to check.
     * @param tetrominoState a boolean indicating whether to update the current position if valid.
     * @return true if the position is valid, false otherwise.
     */
    private boolean isValidPosition(Tetromino tetromino, int x, int y, boolean tetrominoState) { // true - actual tetromino pos, flase - shadow pos
        for (int i = 0; i < 4; i++) {
            int posX = x + tetromino.getX(i);
            int posY = y - tetromino.getY(i);

            // Check boundaries
            if (posX < 0 || posX >= this.boardwidth || posY < 0 || posY >= this.boardheight) {
                return false;
            }

            // Check if the position is already occupied
            if (this.curTetrominoPos(posX, posY) != TetrominoType.NO_BLOCK) {
                return false;
            }
        }

        // Update the current position and repaint necessary
        if (tetrominoState) {
            this.currentTetromino = tetromino;
            this.blockX = x;
            this.blockY = y;
            repaint();
        }

        return true;
    }


    /**
     * Generates a new tetromino and updates the game state accordingly.
     * This method creates two new tetrominoes, adds them to the tetromino queue,
     * and sets the current tetromino to the first one in the queue. It also updates
     * the position of the current tetromino and sets the next tetromino for display.
     * If the current tetromino cannot be placed in the starting position, it sets
     * the current tetromino to NO_BLOCK, stops the timer, and triggers the game over sequence.
     */
    private void newTetromino() {
        // Generate two new tetrominoes and add them to the queue
        for (int i = 0; i < 2; i++) {
            Tetromino tetromino = new Tetromino();
            tetromino.setRandomShape();
            this.tetrominoQueue.add(tetromino);
        }

        // Set the current tetromino to the first in the queue
        this.currentTetromino = this.tetrominoQueue.get(0);
        this.blockX = this.boardwidth / 2 + 1;
        this.blockY = this.boardheight - 1 + this.currentTetromino.getMinY();
        this.tetrominoQueue.remove(0);

        // Update the next tetromino
        this.nextTetromino = this.tetrominoQueue.get(this.tetrominoQueue.size() - 1);
        this.nextTetrominoPanel.setNextTetromino(this.nextTetromino);
        this.tetrominoQueue.set(0, this.nextTetromino);

        // Check if the current tetromino can be placed in the starting position
        if (!this.isValidPosition(this.currentTetromino, this.blockX, this.blockY, true)) {
            this.currentTetromino.setShape(TetrominoType.NO_BLOCK);
            this.timer.stop();
            this.gameStarted = false;
            this.gameOver();
        }
    }

    /**
     * Fixes the current matus.Tetromino's position on the game field.
     * This method is called when the matus.Tetromino has reached its final position
     * and can no longer move down.
     */
    private void fixTetromino() {
        for (int i = 0; i < 4; i++) {
            int x = this.blockX + this.currentTetromino.getX(i);
            int y = this.blockY - this.currentTetromino.getY(i);
            this.gameField[(y * this.boardwidth) + x] = this.currentTetromino.getShape();
        }
        this.clearFullLine();
        if (!this.isStillFalling) {
            this.newTetromino();
        }
    }

    /**
     * Moves the current tetromino down by one unit.
     * If the tetromino cannot move down (i.e., it has reached the bottom of the board
     * or is blocked by another tetromino), it fixes the current tetromino in place
     * on the game board.
     */
    private void moveTetrominoDown() {
        if (!this.isValidPosition(this.currentTetromino, this.blockX, this.blockY - 1, true)) {
            this.fixTetromino();
        }
    }

    /**
     * Drops the current tetromino down to the lowest possible position.
     * This method checks how far the tetromino can move down until it hits the bottom
     * of the board or another tetromino. Once the lowest position is found, it fixes
     * the tetromino in place on the game board.
     */
    private void dropTetrominoDown() {
        int tempY = this.blockY;
        while (tempY > 0) {
            if (!this.isValidPosition(this.currentTetromino, this.blockX, tempY - 1, true)) {
                break;
            }
            --tempY;
        }
        this.fixTetromino();
    }

    /**
     * Handles the game over state by stopping the game timer, marking the game as not started,
     * and stopping the background music. It then creates and displays a game over panel.
     * The game over panel is added to the main frame, and the CardLayout is updated to show
     * the game over screen.
     */
    private void gameOver() {
        this.timer.stop();
        this.gameStarted = false;
        this.backgroundMusic.stopMusic();

//        JPanel panel = this.createGameOverPanel();
        GameOverPanel panel = new GameOverPanel(this, this.mainFrame, this.currentScore, this.currentLevel);
        this.mainFrame.add(panel, "GameOver");
        CardLayout cl = (CardLayout)this.mainFrame.getContentPane().getLayout();
        cl.show(this.mainFrame.getContentPane(), "GameOver");
    }

    /**
     * Creates and returns a JPanel that displays the game over screen.
     * This panel includes a "Game Over!" message, the player's score,
     * the current level, and buttons to restart the game or return to the main menu.
     *
     * @return a JPanel configured to display the game over information and options.
     */
    private JPanel createGameOverPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());
        panel.setBackground(new Color(0x1A3F61));

        JLabel gameOverLabel = new JLabel("Game Over!", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverLabel.setForeground(Color.WHITE);

        JLabel scoreLabel = new JLabel("Score: " + this.currentScore);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));
        scoreLabel.setForeground(Color.WHITE);
        JLabel levelLabel = new JLabel(this.currentLevel);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 25));
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel statPanel = new JPanel();
        statPanel.setLayout(new GridLayout(2, 1));
        statPanel.add(levelLabel);
        statPanel.add(scoreLabel);
        statPanel.setBackground(new Color(0x1A3F61));
        panel.add(gameOverLabel, BorderLayout.NORTH);
        panel.add(statPanel);


        JButton restartButton = new JButton("Restart");
        JButton mainMenuButton = new JButton("matus.Main Menu");

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)TetrisPanel.this.mainFrame.getContentPane().getLayout();
                TetrisPanel.this.start(); // Restart the game
                cl.show(TetrisPanel.this.mainFrame.getContentPane(), "GameBoard");
                requestFocusInWindow();
            }
        });

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)TetrisPanel.this.mainFrame.getContentPane().getLayout();
                cl.show(TetrisPanel.this.mainFrame.getContentPane(), "MainMenu");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(restartButton);
        buttonPanel.add(mainMenuButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        JPanel allPanel = new JPanel();
        allPanel.setLayout(new GridLayout(3, 1));
        allPanel.add(panel);
        allPanel.add(statPanel);
        allPanel.add(buttonPanel);
        return allPanel;
    }


}
