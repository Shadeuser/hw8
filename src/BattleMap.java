import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BattleMap extends JPanel {
    public static final int MODE_H_VS_AI = 0;
    public static final int MODE_H_VS_H = 1;


    private GameWindow gameWindow;

    private int fieldSize;
    private int dotsToWin;

    private int cellHeight;
    private int cellWidth;

    private boolean isInit = false;


    public BattleMap(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setBackground(Color.ORANGE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
    }

    void update(MouseEvent e) {
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        if (!Logic.isGameFinished) {
            Logic.setHumanXY(cellX, cellY);
            if (GameWindow.isSomebodyWins == Logic.HUMAN_WINS) {
                gameWindow.lblWin.setText("    Вы победили !!!");
                gameWindow.lblWin.setVisible(true);
            }
            if (GameWindow.isSomebodyWins == Logic.AI_WINS) {
                gameWindow.lblWin.setText("      АИ победил !!!");
                gameWindow.lblWin.setVisible(true);
            }
            if (GameWindow.isSomebodyWins == Logic.DRAW) {
                gameWindow.lblWin.setText("           Ничья !!!");
                gameWindow.lblWin.setVisible(true);
            }
        }
        repaint();


    }
    void drawWinLine(int cellX, int cellY) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    void render(Graphics g) {

        if (!isInit) {
            return;
        }
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        cellHeight = panelHeight / fieldSize;
        cellWidth = panelWidth / fieldSize;

        for (int i = 0; i < fieldSize; i++) {
            int ii = i * cellHeight;
            g.drawLine(0, ii, panelWidth, ii);
            g.drawLine(ii, 0, ii, panelHeight);
        }
        for (int i = 0; i < Logic.SIZE; i++) {
            for (int j = 0; j < Logic.SIZE; j++) {
                if (Logic.map[i][j] == Logic.DOT_O) {
                    drawO(g, j, i);
                }

                if (Logic.map[i][j] == Logic.DOT_X) {
                    drawX(g, j, i);
                }

            }

        }

    }

    public void drawO(Graphics g, int cellX, int cellY) {
        g.setColor(Color.RED);
        g.drawOval(cellX * cellWidth, cellY * cellHeight, cellWidth, cellHeight);
    }

    public void drawX(Graphics g, int cellX, int cellY) {
        g.setColor(Color.BLUE);
        g.drawLine(cellX * cellWidth, cellY * cellHeight, cellX * cellWidth + cellWidth, cellY * cellHeight + cellHeight);
        g.drawLine(cellX * cellWidth + cellWidth, cellY * cellHeight, cellX * cellWidth, cellY * cellHeight + cellHeight);
    }

    void startNewGame(int gameMode, int fieldSize, int dotsToWin) {
//        System.out.println(gameMode + " " + fieldSize + " " + dotsToWin);
        this.fieldSize = fieldSize;
        this.dotsToWin = dotsToWin;
        isInit = true;
        repaint();


    }


}
