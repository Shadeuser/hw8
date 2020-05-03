import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {


    public static final int WIN_POS_X = 700;
    public static final int WIN_POS_Y = 200;
    private static final int WIN_HEIGHT = 555;
    private static final int WIN_WIDTH = 507;

    public static int isSomebodyWins = 0;

    private StartNewGameWindow startNewGameWindow;
    private BattleMap field;



    JLabel lblWin = new JLabel("");
    public GameWindow() {
        setBounds(WIN_POS_X, WIN_POS_Y, WIN_WIDTH, WIN_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("TicTacToe");
        setResizable(false);



        lblWin.setVisible(false);
        lblWin.setFont(new Font(null, Font.BOLD, 50));
        add(lblWin, BorderLayout.NORTH);

        startNewGameWindow = new StartNewGameWindow(this);
        field = new BattleMap(this);
        add(field);
        setVisible(true);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        JButton btnNewGame = new JButton("Start new game");
        bottomPanel.add(btnNewGame);
        btnNewGame.addActionListener(e -> {
            startNewGameWindow.setVisible(true);

        });
        JButton btnExit = new JButton("Exit");
        bottomPanel.add(btnExit);
        btnExit.addActionListener(e -> {
            System.exit(0);
        });
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);

    }

    void startNewGame(int gameMode, int fieldSize, int dotsToWin) {
        field.startNewGame(gameMode, fieldSize, dotsToWin);
    }

}
