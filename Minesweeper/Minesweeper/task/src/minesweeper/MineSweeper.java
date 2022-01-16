package minesweeper;

import java.util.*;

public class MineSweeper {

    private String[][] battleField;
    private String[][] playerField;
    private boolean[][] exploredField;
    private static final int defaultLength = 9;
    private final int totalRow;
    private final int totalColumn;
    private final int totalMines;
    private int totalMark;
    private int minesLeft;
    private int exploredCounter;

    public MineSweeper(int totalMines) {

        totalRow = defaultLength;
        totalColumn = defaultLength;
        createField(totalRow, totalColumn, totalMines);
        this.totalMines = totalMines;
        minesLeft = totalMines;
        exploredField = new boolean[defaultLength][defaultLength];
    }

    public MineSweeper(int row, int column, int totalMines) {

        totalRow = row;
        totalColumn = column;
        createField(totalRow, totalColumn, totalMines);
        this.totalMines = totalMines;
        minesLeft = totalMines;
        exploredField = new boolean[row][column];
    }

    private void createField(int row, int column, int totalMines) {

        // Create temp list of size row * column
        // Shuffle mines and safe cells within array
        ArrayList<String> list = new ArrayList<>();
        int size = row * column;
        for (int i = 0; i < size; ++i) {
            list.add(".");
        }
        for (int j = 0; j < totalMines; ++j) {
            list.set(j, "X");
        }
        Collections.shuffle(list);

        // Fill battlefield array with value
        battleField = new String[row][column];
        int index = 0;
        for (int k = 0; k < row; ++k) {
            for (int l = 0; l < column; ++l) {
                battleField[k][l] = list.get(index);
                index++;
            }
        }
        setMarkOfMine();

        // Create playerField
        playerField = new String[row][column];
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                playerField[i][j] = ".";
            }
        }
    }

    private void setMarkOfMine() {

        int countMines = 0;
        int[] around = {-1, 0, 1};

        for (int i = 0; i < totalRow; ++i) {
            for (int j = 0; j < totalColumn; ++j) {
                if (!"X".equals(battleField[i][j])) {
                    for (int k : around) {
                        for (int l: around) {
                            if (!(i + k < 0 || j + l < 0 ||
                                    i + k >= totalRow || j + l >= totalColumn)) {
                                if ("X".equals(battleField[i + k][j + l])) countMines++;
                            }
                        }
                    }
                    if (countMines > 0) {
                        battleField[i][j] = String.valueOf(countMines);
                        countMines = 0;
                    }
                }
            }
        }
    }

    private void displayPlayingField() {

        StringBuilder builder = new StringBuilder(" |");
        for (int i = 0; i < totalColumn; ++i) {
            builder.append(i + 1);
        }
        builder.append("|\n-|").append("-".repeat(totalColumn)).append("|\n");
        for (int i = 0; i < totalRow; ++i) {
            builder.append(i + 1).append("|");
            for (int j = 0; j < totalColumn; ++j) {
                builder.append(playerField[i][j]);
            }
            builder.append("|\n");
        }
        builder.append("-|").append("-".repeat(totalColumn)).append("|");
        System.out.println(builder);
    }

    private void freeCell(int row, int column, boolean[][] exploredField) {

        // set condition to end freeing cell
        if (row < 0 || column < 0) return;
        if (row >= totalRow || column >= totalColumn) return;
        if (exploredField[row][column]) return;
        if ("X".equalsIgnoreCase(battleField[row][column])) return;
        if (battleField[row][column].matches("\\d")) {
            playerField[row][column] = battleField[row][column];
            exploredCounter++;
            this.exploredField[row][column] = true;
            return;
        }
        playerField[row][column] = "/";
        this.exploredField[row][column] = true;
        exploredCounter++;

        // recursive freeCell until it's stopped
        int[] around = {-1, 0, 1};
        for (int i : around) {
            for (int j : around) {
                if (!(i == 0 & j == 0)) {
                    freeCell(row + i, column + j, exploredField);
                }
            }
        }

    }

    public void play() {

        boolean isPlaying = true;
        Scanner scanner = new Scanner(System.in);
        String qAction = "Set/unset mines marks or claim a cell as free:";
        String winning = "Congratulations! You found all mines!";
        String lose = "You stepped on a mine and failed!";
        int column;
        int row;
        String command;
        displayPlayingField();

        while (isPlaying) {
            System.out.println(qAction);
            column = scanner.nextInt() - 1;
            row = scanner.nextInt() - 1;
            command = scanner.nextLine().toLowerCase().trim();
            if (row >= totalRow || column >= totalColumn) break;
            if (row < 0 || column < 0) break;
            String actualValue = battleField[row][column];

            // First free command will never hit mine
            if (exploredCounter == 0 && "X".equals(actualValue)) {
                shuffleMine(row, column);
                actualValue = battleField[row][column];
            }
            switch (command) {
                case "free" : {
                    if ("X".equals(actualValue)) {
                        // revealMines method
                        revealMines();
                        displayPlayingField();
                        System.out.println(lose);
                        return;
                    } else if (".".equals(playerField[row][column])) {
                        freeCell(row, column, exploredField);
                    }
                    break;
                }
                case "mine" : {
                    if (".".equals(playerField[row][column])) {
                        playerField[row][column] = "*";
                        if ("X".equals(battleField[row][column])) minesLeft--;
                        totalMark++;
                    } else if ("*".equals(playerField[row][column])) {
                        playerField[row][column] = ".";
                        if ("X".equals(battleField[row][column])) minesLeft++;
                        totalMark--;
                    }
                    break;
                }
                default: {
                }
            }
            displayPlayingField();
            isPlaying = ! isWinning();
        }
        System.out.println(winning);
    }

    private boolean isWinning() {

        if (totalMark == totalMines && minesLeft == 0) {
            return true;
        } else {
            return exploredCounter == totalRow * totalColumn - totalMines;
        }
    }

    private void revealMines() {

        for (int i = 0; i < totalRow; ++i) {
            for (int j = 0; j < totalColumn; ++j) {
                if ("X".equals(battleField[i][j])) {
                    playerField[i][j] = "X";
                }
            }
        }
    }

    private void shuffleMine(int newRow, int newColumn) {

        Random random = new Random();
        int oldRow = newRow;
        int oldColumn = newColumn;
        while ("X".equals(battleField[newRow][newColumn])) {
            newRow = random.nextInt(totalRow - 1);
            newColumn = random.nextInt(totalColumn - 1);
        }
        battleField[newRow][newColumn] = "X";
        battleField[oldRow][oldColumn] = ".";
        resetNumberMark();
        setMarkOfMine();
    }

    private void resetNumberMark() {

        for (int i = 0; i < totalRow; ++i) {
            for (int j = 0; j < totalColumn; ++j) {
                if (battleField[i][j].matches("\\d")) {
                    battleField[i][j] = ".";
                }
            }
        }
    }

    private void displayActualField() {

        for (String[] strings : battleField) {
            System.out.println();
            for (String s : strings) {
                System.out.print(s + " ");
            }
        }
        System.out.println();
    }
}
