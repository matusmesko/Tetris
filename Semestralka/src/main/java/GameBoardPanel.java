import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameBoardPanel extends JPanel implements ActionListener {
    private final int BOARDWIDTH = 10; // game board x size
    private final int BOARDHEIGHT = 22; // game board y size

    // game status & timer
    private Timer timer;
    private boolean isBlockFalling = false;
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
    private Tetrominoes[] gameField;
    private Color[] colorTable;

    // adjusting game status
    private String currentStatus;
    private String currentLevel;
    private int currentTimerResolution;


    private MusicController backgroundMusic;
    private MusicController music1;
    private MusicController music2;

    private NextTetrominoPanel nextTetrominoPanel;
    private JFrame mainFrame;

    public GameBoardPanel(int timerResolution, MusicController main, MusicController m1, MusicController m2, NextTetrominoPanel nextTetrominoPanel, JFrame mainFrame) {
        setFocusable(true);
        setBackground(new Color(0, 30, 30));
        this.mainFrame = mainFrame;
        this.tetrominoQueue = new ArrayList<>();
        this.nextTetrominoPanel = nextTetrominoPanel;
        currentTetromino = new Tetromino();
        nextTetromino = new Tetromino();
        timer = new Timer(timerResolution, this);
        timer.start(); // activate timer
        currentTimerResolution = timerResolution;
        this.backgroundMusic = main;
        this.music1 = m1;
        this.music2 = m2;

        this.gameField = new Tetrominoes[BOARDWIDTH * BOARDHEIGHT];
        this.colorTable = Utils.getColorTable();



        // keyboard listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameStarted || currentTetromino.getShape() == Tetrominoes.NO_BLOCK) return;


                int keycode = e.getKeyCode();

                if (keycode == 'p' || keycode == 'P' || keycode == KeyEvent.VK_ESCAPE) {
                    pause();
                    return;
                }

                if (gamePaused) return;


                switch (keycode) {
                    case 'a':
                    case 'A':
                    case KeyEvent.VK_LEFT:
                        isMovable(currentTetromino, blockX - 1, blockY);
                        break;
                    case 'd':
                    case 'D':
                    case KeyEvent.VK_RIGHT:
                        isMovable(currentTetromino, blockX + 1, blockY);
                        break;
                    case 'w':
                    case 'W':
                    case KeyEvent.VK_UP:
                        isMovable(currentTetromino.rotateRight(), blockX, blockY);
                        break;
                    case 's':
                    case 'S':
                    case KeyEvent.VK_DOWN:
                        moveBlockDown();
                        break;
                    case KeyEvent.VK_SPACE:
                        dropBlockDown();
                        break;
                    case 'p':
                    case KeyEvent.VK_ESCAPE:
                    case 'P':
                        pause();
                        break;
                    case 'r':
                    case 'R':
                        gameOver();
                        break;
                }

            }
        });

        initBoard();
    }

    // adjusting game level
    private void setTimer() {

        switch (currentScore / 10) {
            case 10:
                currentTimerResolution = 100;
                break;
            case 9:
                currentTimerResolution = 140;
                break;
            case 8:
                currentTimerResolution = 180;
                break;
            case 7:
                currentTimerResolution = 220;
                break;
            case 6:
                currentTimerResolution = 260;
                break;
            case 5:
                currentTimerResolution = 300;
                break;
            case 4:
                currentTimerResolution = 340;
                break;
            case 3:
                currentTimerResolution = 380;
                break;
            case 2:
                currentTimerResolution = 420;
                break;
            case 1:
                currentTimerResolution = 460;
                break;
        }

        timer.setDelay(currentTimerResolution);

    }

    // initialize game board
    private void initBoard() {
        for (int i = 0; i < BOARDWIDTH * BOARDHEIGHT; i++) {
            gameField[i] = Tetrominoes.NO_BLOCK;
        }
    }

    // timer callback
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isBlockFalling) {
            isBlockFalling = !isBlockFalling; // toggle status
            newTetromino();
        } else {
            moveBlockDown();
        }
    }

    public void start() {
        if (gamePaused) return;


        gameStarted = true;
        isBlockFalling = false;
        currentScore = 0;
        initBoard();

        newTetromino();
        timer.start();
        try {
            this.backgroundMusic.playMusicLoop("/main.wav");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (!gameStarted) {
            return;
        }

        gamePaused = !gamePaused;
        if (gamePaused) {
            timer.stop();
            this.backgroundMusic.pauseMusic();
        } else {
            timer.start();
            this.backgroundMusic.resumeMusic();
        }

        repaint();
    }


    // calculates actual size of tetromino on screen
    private int blockWidth() {
        return (int) getSize().getWidth() / BOARDWIDTH;
    }

    private int blockHeight() {
        return (int) getSize().getHeight() / BOARDHEIGHT;
    }

    // current tetromino position in array (atom)
    Tetrominoes curTetrominoPos(int x, int y) {
        return gameField[(y * BOARDWIDTH) + x];
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        if (!gamePaused) {
            currentStatus =  "Score: " + currentScore;
            currentLevel = "Level: " + (currentScore / 10 + 1);
        } else {
            currentStatus = "Paused";
            currentLevel = "";
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri", Font.BOLD, 28));
        g.drawString(currentStatus, 15, 35);
        g.drawString(currentLevel, 15, 70);

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BOARDHEIGHT * blockHeight();

        // rendering - shadow of tetromino
        int tempY = blockY;
        while (tempY > 0) {
            if (!isPositionValid(currentTetromino, blockX, tempY - 1, false)) {
                break;
            }
            tempY--;
        }
        for (int i = 0; i < 4; i++) {
            int x = blockX + currentTetromino.getX(i);
            int y = tempY - currentTetromino.getY(i);
            drawTetromino(g, x * blockWidth(), boardTop + (BOARDHEIGHT - y - 1) * blockHeight(),
                    currentTetromino.getShape(),
                    true);
        }

        // rendering - game board
        for (int i = 0; i < BOARDHEIGHT; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                Tetrominoes shape = curTetrominoPos(j, BOARDHEIGHT - i - 1);
                if (shape != Tetrominoes.NO_BLOCK) {
                    drawTetromino(g, j * blockWidth(), boardTop + i * blockHeight(), shape, false);
                }
            }
        }

        // rendering - current tetromino
        if (currentTetromino.getShape() != Tetrominoes.NO_BLOCK) {
            for (int i = 0; i < 4; i++) {
                int x = blockX + currentTetromino.getX(i);
                int y = blockY - currentTetromino.getY(i);
                drawTetromino(g, x * blockWidth(), boardTop + (BOARDHEIGHT - y - 1) * blockHeight(),
                        currentTetromino.getShape(), false);


            }
        }

    }

    private void drawTetromino(Graphics g, int x, int y, Tetrominoes bs, boolean isShadow) {
        Color curColor = colorTable[bs.ordinal()];

        if (!isShadow) {
            g.setColor(curColor);
            g.fillRect(x + 1, y + 1, blockWidth() - 2, blockHeight() - 2);
        } else {
            g.setColor(curColor.darker().darker());
            g.fillRect(x + 1, y + 1, blockWidth() - 2, blockHeight() - 2);
        }
    }

    private void clearFullLine() {
        int fullLines = 0;

        for (int i = BOARDHEIGHT - 1; i >= 0; i--) {
            boolean isFull = true;

            for (int j = 0; j < BOARDWIDTH; j++) {
                if (curTetrominoPos(j, i) == Tetrominoes.NO_BLOCK) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                ++fullLines;
                for (int k = i; k < BOARDHEIGHT - 1; k++) {
                    for (int l = 0; l < BOARDWIDTH; ++l) {
                        gameField[(k * BOARDWIDTH) + l] = curTetrominoPos(l, k + 1);
                    }
                }
                try {
                    this.music2.playMusic("/on.wav");
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (fullLines > 0) {
            currentScore += fullLines;
            isBlockFalling = true;
            currentTetromino.setShape(Tetrominoes.NO_BLOCK);
            setTimer();
            repaint();
        }
        try {
            this.music1.playMusic("/off.wav");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // true - actual tetromino pos
    // flase - shadow pos
    private boolean isPositionValid(Tetromino chkBlock, int chkX, int chkY, boolean flag) {
        for (int i = 0; i < 4; i++) {
            int x = chkX + chkBlock.getX(i);
            int y = chkY - chkBlock.getY(i);
            if (x < 0 || x >= BOARDWIDTH || y < 0 || y >= BOARDHEIGHT) {
                return false;
            }
            if (curTetrominoPos(x, y) != Tetrominoes.NO_BLOCK) {
                return false;
            }
        }

        if (flag) {
            currentTetromino = chkBlock;
            blockX = chkX;
            blockY = chkY;
            repaint();
        }

        return true;
    }

    private boolean isMovable(Tetromino chkBlock, int chkX, int chkY) {
        return isPositionValid(chkBlock, chkX, chkY, true);
    }

    private void newTetromino() {
        for (int i = 0; i < 2; i++) {
            Tetromino tetromino = new Tetromino();
            tetromino.setRandomShape();
            tetrominoQueue.add(tetromino);
        }

        // 0 1
        currentTetromino = this.tetrominoQueue.get(0);
        blockX = BOARDWIDTH / 2 + 1;
        blockY = BOARDHEIGHT - 1 + currentTetromino.minY();
        this.tetrominoQueue.remove(0);
        // 1

        nextTetromino = this.tetrominoQueue.get(this.tetrominoQueue.size() - 1);
        nextTetrominoPanel.setNextTetromino(nextTetromino);
        this.tetrominoQueue.set(0, nextTetromino);
        // 0

        if (!isMovable(currentTetromino, blockX, blockY)) {
            currentTetromino.setShape(Tetrominoes.NO_BLOCK);
            timer.stop();
            gameStarted = false;
            gameOver();
        }
    }

    private void fixTetromino() {
        for (int i = 0; i < 4; i++) {
            int x = blockX + currentTetromino.getX(i);
            int y = blockY - currentTetromino.getY(i);
            gameField[(y * BOARDWIDTH) + x] = currentTetromino.getShape();
        }

        clearFullLine();

        if (!isBlockFalling) {
            newTetromino();
        }

    }

    private void moveBlockDown() {
        if (!isMovable(currentTetromino, blockX, blockY - 1)) {
            fixTetromino();
        }
    }

    private void dropBlockDown() {
        int tempY = blockY;
        while (tempY > 0) {
            if (!isMovable(currentTetromino, blockX, tempY - 1)) {
                break;
            }
            --tempY;
        }
        fixTetromino();
    }
    

    private void gameOver() {
        this.backgroundMusic.stopMusic();
        JPanel panel = createGameOverPanel();
        this.mainFrame.add(panel, "GameOver");
        CardLayout cl = (CardLayout) mainFrame.getContentPane().getLayout();
        cl.show(mainFrame.getContentPane(), "GameOver");
    }

    private JPanel createGameOverPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());
        panel.setBackground(new Color(0x1A3F61));

        JLabel gameOverLabel = new JLabel("Game Over!", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverLabel.setForeground(Color.WHITE);

        JLabel scoreLabel = new JLabel("Score: " + currentScore);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));
        scoreLabel.setForeground(Color.WHITE);
        JLabel levelLabel = new JLabel(currentLevel);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 25));
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel statPanel = new JPanel();
        statPanel.setLayout(new GridLayout(2,1));
        statPanel.add(levelLabel);
        statPanel.add(scoreLabel);
        statPanel.setBackground(new Color(0x1A3F61));
        panel.add(gameOverLabel, BorderLayout.NORTH);
        panel.add(statPanel);


        JButton restartButton = new JButton("Restart");
        JButton mainMenuButton = new JButton("Main Menu");

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainFrame.getContentPane().getLayout();
                start(); // Restart the game
                cl.show(mainFrame.getContentPane(), "GameBoard");
                requestFocusInWindow();
            }
        });

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainFrame.getContentPane().getLayout();
                cl.show(mainFrame.getContentPane(), "MainMenu");
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
