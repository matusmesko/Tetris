package matus.panels;

import matus.MainWindow;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;


public class MainMenuPanel extends JPanel {

    private final MainWindow mainWindow;

    /**
     * Constructs the matus.panels.MainMenuPanel.
     *
     * @param mainWindow the parent GameWindow instance.
     */
    public MainMenuPanel(MainWindow mainWindow) {
        super(new BorderLayout());
        this.mainWindow = mainWindow;
        setBackground(new Color(0x1A3F61));

        // Add game logo
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/tetris-logo.png")));
        add(imageLabel, BorderLayout.NORTH);

        // Create buttons
        JButton playButton = this.createButton("Play", e -> mainWindow.switchToPanel("GameBoard", true));
        JButton settingsButton = this.createButton("Settings", e -> mainWindow.switchToPanel("Settings", false));

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(playButton);
        buttonPanel.add(settingsButton);
        buttonPanel.setBackground(new Color(0x1A3F61));

        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a JButton with the specified text and action listener.
     * This method simplifies the creation of buttons by encapsulating
     * the button creation and action listener assignment.
     *
     * @param text the text to be displayed on the button.
     * @param actionListener the ActionListener to be added to the button.
     * @return the created JButton.
     */
    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }
}
