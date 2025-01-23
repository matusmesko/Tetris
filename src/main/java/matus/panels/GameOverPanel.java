package matus.panels;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

public class GameOverPanel extends JPanel {

    private final TetrisPanel tetrisPanel;
    private final JFrame mainFrame;
    private final int currentScore;
    private final String currentLevel;

    /**
     * Constructor for game over panel which displays when game ends.
     * Display basic information about game like score and level,
     * and give options to restart game or go to main menu.
     *
     * @param tetrisPanel   the matus.panels.TetrisPanel instance (for restarting the game).
     * @param mainFrame     the main application frame.
     * @param currentScore  the player's current score to display.
     * @param currentLevel  the player's current level to display.
     */
    public GameOverPanel(TetrisPanel tetrisPanel, JFrame mainFrame, int currentScore, String currentLevel) {
        this.tetrisPanel = tetrisPanel;
        this.mainFrame = mainFrame;
        this.currentScore = currentScore;
        this.currentLevel = currentLevel;

        setLayout(new GridLayout(3, 1));
        setBackground(new Color(0x1A3F61));

        add(this.createTitlePanel(), BorderLayout.NORTH);
        add(this.createStatPanel(), BorderLayout.CENTER);
        add(this.createButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * Creates the title panel with a "Game Over" label.
     *
     * @return the title panel.
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0x1A3F61));

        JLabel gameOverLabel = new JLabel("Game Over!", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverLabel.setForeground(Color.WHITE);

        panel.add(gameOverLabel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the statistics panel displaying the score and level.
     *
     * @return the statistics panel.
     */
    private JPanel createStatPanel() {
        JPanel statPanel = new JPanel(new GridLayout(2, 1));
        statPanel.setBackground(new Color(0x1A3F61));

        JLabel scoreLabel = new JLabel("Score: " + this.currentScore, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));
        scoreLabel.setForeground(Color.WHITE);

        JLabel levelLabel = new JLabel(this.currentLevel, SwingConstants.CENTER);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 25));
        levelLabel.setForeground(Color.WHITE);

        statPanel.add(levelLabel);
        statPanel.add(scoreLabel);
        return statPanel;
    }

    /**
     * Creates the button panel with "Restart" and "matus.Main Menu" buttons.
     *
     * @return the button panel.
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10 , 10));
        buttonPanel.setBackground(new Color(0x1A3F61));

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)GameOverPanel.this.mainFrame.getContentPane().getLayout();
                GameOverPanel.this.tetrisPanel.start(); // Restart the game
                cl.show(GameOverPanel.this.mainFrame.getContentPane(), "GameBoard");
                GameOverPanel.this.tetrisPanel.requestFocusInWindow();
            }
        });

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)GameOverPanel.this.mainFrame.getContentPane().getLayout();
                cl.show(GameOverPanel.this.mainFrame.getContentPane(), "MainMenu");
            }
        });

        buttonPanel.add(restartButton);
        buttonPanel.add(mainMenuButton);
        return buttonPanel;
    }
}
