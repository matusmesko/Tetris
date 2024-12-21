import javax.swing.*;
import java.awt.*;

public class GameWindow implements Runnable{

    private final JFrame mainFrame;
    private final GameBoardPanel gameBoard;

    public GameWindow() {
        this.mainFrame = new JFrame();
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setTitle("Tetris");
        this.mainFrame.setSize(800, 814);
        this.mainFrame.setResizable(false);

        this.mainFrame.setLayout(new GridLayout(1, 2));

        // you can adjust timer resolution here. but it's ideal value for this game.
        this.gameBoard = new GameBoardPanel(this, 500);
        this.mainFrame.add(gameBoard);
        this.menuPanel = new MenuPanel();
        this.showBlockPanel = new ShowBlockPanel();
        this.mainFrame.add(showBlockPanel);
        //this.mainFrame.add(this.menuPanel);

    }

    @Override
    public void run() {
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setVisible(true);
        this.gameBoard.start();
    }
}
