import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class Logic {
    static int SIZE;
    static int DOTS_TO_WIN = 4;
    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';
    static final int IN_PLAY = 0;
    static final int HUMAN_WINS = 1;
    static final int AI_WINS = 2;
    static final int DRAW = 3;


    static char[][] map;
    static Random random = new Random();
    static boolean isGameFinished = false;

    public static void go() {
        isGameFinished = true;

        if (whoWins() == DOT_X) {
            GameWindow.isSomebodyWins = HUMAN_WINS;
            return;
        }
        if (whoWins() == DOT_O) {
            GameWindow.isSomebodyWins = AI_WINS;
            return;
        }
        if (whoWins() == 'D') {
            GameWindow.isSomebodyWins = DRAW;
        }
        smartAiTurn();

        if (whoWins() == DOT_X) {
            GameWindow.isSomebodyWins = 1;
            return;
        }
        if (whoWins() == DOT_O) {
            GameWindow.isSomebodyWins = 2;
            return;
        }
        if (whoWins() == 'D') {
            GameWindow.isSomebodyWins = DRAW;
        }
        isGameFinished = false;
    }

    public static void setHumanXY(int x, int y) {
        if (isCellValid(y, x)) {
            map[y][x] = DOT_X;
            go();
        }

    }

    public static void initMap() {
        isGameFinished = false;
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;

            }

        }
    }


    public static boolean isCellValid(int y, int x) {
        if (x < 0 || x > SIZE - 1 || y < 0 || y > SIZE - 1) {
            return false;
        }
        return map[y][x] == DOT_EMPTY;
    }

    public static void aiTurn() {
        int x;
        int y;
        do {
            y = random.nextInt(SIZE);
            x = random.nextInt(SIZE);
        } while (!isCellValid(y, x));
        map[y][x] = DOT_O;
    }

    public static char whoWins() {
        int dotBusy = 0;
        for (int i = 0; i <= SIZE - 1; i++) {
            for (int j = 0; j <= SIZE - 1; j++) {
                int winRight = 0;
                int winDown = 0;
                int winRightAndDown = 0;
                int winRightAndUp = 0;
                char sign = map[i][j];
                for (int ii = 0; ii <= SIZE - 1; ii++) {
                    if (ii + i <= SIZE - 1) {
                        if (map[ii + i][j] == sign) {
                            winRight++;
                        } else {
                            winRight = 0;
                        }
                    }
                    if (winRight >= DOTS_TO_WIN) {
                        return sign;

                    }
                    if (ii + j <= SIZE - 1) {
                        if (map[i][ii + j] == sign) {
                            winDown++;
                        } else {
                            winDown = 0;
                        }
                    }
                    if (winDown >= DOTS_TO_WIN) {
                        return sign;
                    }
                    if (ii + i <= SIZE - 1 && ii + j <= SIZE - 1) {
                        if (map[i + ii][j + ii] == sign) {
                            winRightAndDown++;
                        } else {
                            winRightAndDown = 0;
                        }
                    }
                    if (winRightAndDown >= DOTS_TO_WIN) {
                        return sign;
                    }
                    if (i - ii >= 0 && j + ii <= SIZE - 1) {
                        if (map[i - ii][j + ii] == sign) {
                            winRightAndUp++;
                        } else {
                            winRightAndUp = 0;
                        }

                    }
                    if (winRightAndUp >= DOTS_TO_WIN) {
                        return sign;
                    }
                }
                if (map[i][j] != DOT_EMPTY) {
                    dotBusy++;
                }
            }
        }
        if (dotBusy == SIZE * SIZE) {
            return 'D';
        }
        return '!';
    }

    public static void smartAiTurn() {
        boolean isOut = false;
        //Блокировка хода игрока
        for (int i = 0; i <= SIZE - 1; i++) {
            if (isNewBlockFound(i)) {
                return;
            }
        }

        if (!isOut) {
            aiTurn();
        }
    }

    public static boolean isNewBlockFound(int z) {
        int J_Line = 0;
        int I_Line = 0;
        int emptyJ = -1;
        int emptyI = -1;

        for (int ii = 0; ii <= SIZE - 1; ii++) {
            if (map[z][ii] == DOT_X) {
                J_Line++;
            } else if (map[z][ii] == DOT_EMPTY) {
                emptyJ = ii;
            }
            if (map[ii][z] == DOT_X) {
                I_Line++;
            } else if (map[ii][z] == DOT_EMPTY) {
                emptyI = ii;
            }
        }
        if (J_Line == DOTS_TO_WIN - 1 && emptyJ > -1) {
            // Ход на map[i][emptyJ]
            map[z][emptyJ] = DOT_O;
//            System.out.println("Закрыт по горизонтали");
            return true;

        }

        if (I_Line == DOTS_TO_WIN - 1 && emptyI > -1) {
            System.out.println(" ");
            System.out.println(emptyI + "  " + z);
//            System.out.println("Закрыт по вертикали");
            map[emptyI][z] = DOT_O;
            return true;
        }

        int emptyZIleft = -1;
        int emptyZJleft = -1;
        int IJleft_Line = 0;

        int IJright_Line = 0;
        int emptyZIright = -1;
        int emptyZJright = -1;
        int zj = 0;
        for (int zi = z; zi <= SIZE - 1; zi++) {
            if (map[zi][zj] == DOT_X) {
                IJleft_Line++;
            } else if (map[zi][zj] == DOT_EMPTY) {
                emptyZIleft = zi;
                emptyZJleft = zj;

            }
            if (map[zi][SIZE - 1 - zj] == DOT_X) {
                IJright_Line++;
            } else if (map[zi][SIZE - 1 - zj] == DOT_EMPTY) {
                emptyZIright = zi;
                emptyZJright = SIZE - 1 - zj;
            }
            zj++;
        }
        if (IJleft_Line == DOTS_TO_WIN - 1 && emptyZIleft > -1) {
//            System.out.println();
//            System.out.println("Слева направо вниз");
            map[emptyZIleft][emptyZJleft] = DOT_O;
            return true;
        }

        if (IJright_Line == DOTS_TO_WIN - 1 && emptyZIright > -1) {
//            System.out.println();
//            System.out.println("Справа налево вниз");
            map[emptyZIright][emptyZJright] = DOT_O;
            return true;
        }
        return false;
    }

}
