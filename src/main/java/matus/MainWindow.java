package matus;

import matus.panels.MainMenuPanel;
import matus.panels.NextTetrominoPanel;
import matus.panels.SettingsPanel;
import matus.panels.TetrisPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.GridLayout;


public class MainWindow implements Runnable {
    private final JFrame mainFrame;
    private final MainMenuPanel mainMenu;
    private final SettingsPanel settingsPanel;
    private final NextTetrominoPanel nextTetrominoPanel;
    private TetrisPanel tetrisPanel;
    private final MusicController mainMusic;
    private final MusicController miscMusic;

    /**
     * Constructs the main window for the Tetris game.
     * This constructor initializes the JFrame, sets its properties,
     * and adds various panels to the frame, including the main menu,
     * game board, and settings panel.
     */
    public MainWindow() {
        this.mainFrame = new JFrame("Tetris");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(800, 814);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLayout(new CardLayout());

        this.mainMusic = new MusicController(true);
        this.miscMusic = new MusicController(true);

        // Initialize the settings panel, main menu and next tetromino panel
        this.settingsPanel = new SettingsPanel(this);
        this.mainMenu = new MainMenuPanel(this);
        this.nextTetrominoPanel = new NextTetrominoPanel();

        // Add panels to the main frame with CardLayout
        this.mainFrame.add(this.mainMenu, "MainMenu");
        this.mainFrame.add(this.createGameBoard(), "GameBoard");
        this.mainFrame.add(this.settingsPanel, "Settings");
    }

    /**
     * Entry point to run the application.
     * Sets the main frame visible and positions it in the center of the screen.
     */
    @Override
    public void run() {
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setVisible(true);
    }

    /**
     * Creates the game board panel, including the game board and next tetromino panel.
     *
     * @return the game board panel.
     */
    private JPanel createGameBoard() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        this.tetrisPanel = new TetrisPanel(500, this.mainMusic, this.miscMusic, this.nextTetrominoPanel, this.mainFrame);
        panel.add(this.tetrisPanel);
        panel.add(this.nextTetrominoPanel);
        return panel;
    }

    /**
     * Switches to the specified panel by its name and optionally starts the game.
     *
     * @param panelName the name of the panel to display.
     * @param startGame whether to start the game when switching to the panel.
     */
    public void switchToPanel(String panelName, boolean startGame) {
        CardLayout cl = (CardLayout)this.mainFrame.getContentPane().getLayout();
        cl.show(this.mainFrame.getContentPane(), panelName);
        if (startGame) {
            this.tetrisPanel.start();
            this.tetrisPanel.requestFocusInWindow();
        }
    }

    /**
     * Get music controller of background music
     * @return background music controller
     */
    public MusicController getMainMusicController() {
        return this.mainMusic;
    }
}
