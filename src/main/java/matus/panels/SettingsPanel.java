package matus.panels;

import matus.MainWindow;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {

    private final JToggleButton toggleButton;
    private final MainWindow mainWindow;

    /**
     * Constructs the matus.panels.SettingsPanel for the Tetris game.
     * This constructor initializes the panel with a title, a toggle button for music,
     * and a back button to return to the main menu.
     *
     * @param mainWindow the matus.MainWindow instance that contains the game.
     */
    public SettingsPanel(MainWindow mainWindow) {
        super(new BorderLayout());
        this.mainWindow = mainWindow;
        setBackground(new Color(0x1A3F61));

        JLabel title = new JLabel("Settings", SwingConstants.CENTER);
        title.setFont(new Font("Calibri", Font.BOLD, 100));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JButton backButton = this.createButton("Back", e -> mainWindow.switchToPanel("MainMenu", false));
        this.toggleButton = new JToggleButton("Music On");

        this.toggleButton.addActionListener(e -> this.toggleMusic());

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(this.toggleButton);
        buttonPanel.add(backButton);
        buttonPanel.setBackground(new Color(0x1A3F61));

        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Toggles the music on or off based on the state of the toggle button.
     * Updates the button text to reflect the current state and informs the
     * matus.MainWindow's music controller whether music can be played.
     */
    private void toggleMusic() {
        boolean isMusicOn = this.toggleButton.isSelected();
        this.toggleButton.setText(isMusicOn ? "Music Off" : "Music On");
        this.mainWindow.getMainMusicController().setCanPlay(!isMusicOn);
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
