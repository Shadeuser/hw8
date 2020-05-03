import javax.swing.*;
import java.awt.*;

public class StartNewGameWindow extends JFrame {
    private static final int WIN_HEIGHT = 450;
    private static final int WIN_WIDTH = 470;
    public static final int WIN_POS_X = 720;
    public static final int WIN_POS_Y = 250;
    public static final int MIN_FIELD_SIZE = 3;
    public static final int MAX_FIELD_SIZE = 10;
    public static final int MIN_DOTS_TO_WIN = 3;
    public static final int MAX_DOTS_TO_WIN = 3;


    private JRadioButton jrbHumVsAi;
    private JRadioButton jrbHumVsHum;
    private ButtonGroup gameMode;

    private GameWindow gameWindow;

    private JSlider jsFieldSize;
    private JSlider jsDotsToWin;

    public StartNewGameWindow(GameWindow gameWindow) {
        setBounds(WIN_POS_X, WIN_POS_Y, WIN_WIDTH, WIN_HEIGHT);
        setTitle("TicTacToe. Settings");
        setResizable(false);
        this.gameWindow = gameWindow;
        setLayout(new GridLayout(10, 1));

        jrbHumVsAi = new JRadioButton("Human Vs Ai", true);
        jrbHumVsHum = new JRadioButton("Human Vs Human");

        gameMode = new ButtonGroup();
        gameMode.add(jrbHumVsAi);
        gameMode.add(jrbHumVsHum);

        add( new JLabel("Choice game mode:"));
        add(jrbHumVsAi);
        add(jrbHumVsHum);

        add(new JLabel("Choose field size:"));
        jsFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);

        jsFieldSize.setMajorTickSpacing(1);
        jsFieldSize.setPaintLabels(true);
        jsFieldSize.setPaintTicks(true);
        add(jsFieldSize);
        jsFieldSize.addChangeListener(e -> {
            int fieldSize =jsFieldSize.getValue();
            jsDotsToWin.setMaximum(fieldSize);
        });



        add(new JLabel(("Choose dots to win:")));
        jsDotsToWin = new JSlider(MIN_DOTS_TO_WIN, MAX_DOTS_TO_WIN, MIN_DOTS_TO_WIN);
        jsDotsToWin.setMajorTickSpacing(1);
        jsDotsToWin.setPaintLabels(true);
        jsDotsToWin.setPaintTicks(true);
        add(jsDotsToWin);

        JButton btnBackToGame = new JButton("Back to game page");
        add(btnBackToGame);
        btnBackToGame.addActionListener(e -> {
            int fieldSize = jsFieldSize.getValue();
            int dotsToWin = jsDotsToWin.getValue();
            int gameMode = jrbHumVsAi.isSelected() ? BattleMap.MODE_H_VS_AI : BattleMap.MODE_H_VS_H;
            gameWindow.lblWin.setVisible(false);
            gameWindow.isSomebodyWins = Logic.IN_PLAY;
            setVisible(false);

            gameWindow.startNewGame(gameMode, fieldSize, dotsToWin);
            Logic.SIZE = fieldSize;
            Logic.DOTS_TO_WIN = dotsToWin;
            Logic.initMap();
        });




    }
}
