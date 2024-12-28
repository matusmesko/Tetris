import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow implements Runnable{

    private final JFrame mainFrame;
    private GameBoardPanel gameBoard;
    private final JPanel mainMenu;
    private final JPanel settingsPanel;
    private MusicController mainMusic;
    private MusicController m1;
    private MusicController m2;
    private JToggleButton toggleButton;
    private final NextTetrominoPanel nextTetrominoPanel;
//    private final JPanel gameOverPanel;

    public GameWindow() {
        this.mainFrame = new JFrame();
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setTitle("Tetris");
        this.mainFrame.setSize(800, 814);
        this.mainFrame.setResizable(false);
        this.mainMusic = new MusicController(true);
        this.m1 = new MusicController(true);
        this.m2 = new MusicController(true);

        //this.mainFrame.setLayout(new GridLayout(1, 2));
        this.mainFrame.setLayout(new CardLayout());
        // you can adjust timer resolution here. but it's ideal value for this game.

        this.settingsPanel = createSettingsPanel();
        this.mainMenu = createMainMenuPanel();
//        this.gameOverPanel = createGameOverPanel();
        this.nextTetrominoPanel = new NextTetrominoPanel();
        this.mainFrame.add(mainMenu, "MainMenu");
        this.mainFrame.add(createGameBoard(this.nextTetrominoPanel), "GameBoard");
        this.mainFrame.add(settingsPanel, "Settings");
//        this.mainFrame.add(gameOverPanel, "GameOver");

    }

    @Override
    public void run() {
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setVisible(true);
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0x1A3F61));

        // Add image at the top
        //JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/tetris.png"));
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/tetris.png")));
        panel.add(imageLabel, BorderLayout.NORTH);

        // Create buttons
        JButton playButton = new JButton("Play");
        JButton settingsButton = new JButton("Settings");

        // Add action listeners
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainFrame.getContentPane().getLayout();
                cl.show(mainFrame.getContentPane(), "GameBoard");
                gameBoard.start(); // Start the game
                gameBoard.requestFocusInWindow(); // Request focus for key events
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainFrame.getContentPane().getLayout();
                cl.show(mainFrame.getContentPane(), "Settings");
                settingsPanel.requestFocusInWindow();
            }
        });

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(playButton);
        buttonPanel.add(settingsButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0x1A3F61));

        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Calibri", Font.BOLD, 100));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title, BorderLayout.NORTH);
        JButton backButton = new JButton("Back");
        toggleButton = new JToggleButton("Music on");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainFrame.getContentPane().getLayout();
                cl.show(mainFrame.getContentPane(), "MainMenu");
                settingsPanel.requestFocusInWindow();
            }
        });

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggleButton.isSelected()) {
                    toggleButton.setText("Music Off");
                    mainMusic.setCanPlay(false);
                } else {
                    toggleButton.setText("Music On");
                    mainMusic.setCanPlay(true);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(toggleButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createGameBoard(NextTetrominoPanel nextTetrominoPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        this.gameBoard = new GameBoardPanel(500, mainMusic, m1, m2, nextTetrominoPanel, this.mainFrame);
        panel.add(this.gameBoard);
        panel.add(nextTetrominoPanel);
        return panel;
    }

}
