package minesweeper;

import java.util.ArrayDeque;
import java.util.Random;

public class FieldMinesweeper {
    private final int fieldSizeX;
    private final int fieldSizeY;
    private final char[][] fieldInGame;
    private final char[][] fieldWithAnswer;
    int amountOfMines;
    private boolean isSteppedOnMine = false;
    private final ArrayDeque<Integer> arrayDequeColumnToCheck = new ArrayDeque<>();
    private final ArrayDeque<Integer> arrayDequeRowToCheck = new ArrayDeque<>();

    FieldMinesweeper(int fieldSizeX, int fieldSizeY) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.fieldInGame = createField(fieldSizeX, fieldSizeY);
        this.fieldWithAnswer = createField(fieldSizeX, fieldSizeY);
    }

    public char[][] createField(int fieldSizeX, int fieldSizeY) {
        char[][] field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = '.';
            }
        }
        return field;
    }

    public void setAmountOfMines(int amountOfMines) {
        this.amountOfMines = amountOfMines;
    }

    public void fillMinesOnField(int currentX, int currentY) {
        Random random = new Random();

        int currentAmountOfMines = 0;
        while (currentAmountOfMines != amountOfMines) {
            int randomX = random.nextInt(fieldSizeX);
            int randomY = random.nextInt(fieldSizeY);
            if (fieldWithAnswer[randomY][randomX] != 'X'
                    && currentX != randomX
                    || currentY != randomY) {
                fieldWithAnswer[randomY][randomX] = 'X';
                currentAmountOfMines++;
            }
        }
        this.fillFieldWithAmountOfMinesAround();
    }

    public void printField() {
        System.out.print(" |");
        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1);
        }
        System.out.println("|");
        System.out.println("-|---------|");
        for (int i = 0; i < fieldSizeY; i++) {
            System.out.printf("%d|", i + 1);
            for (int j = 0; j < fieldSizeX; j++) {
                System.out.print(fieldInGame[i][j]);
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }

    private int checkMinesAround(int x, int y) {
        int amountOfMines = 0;
        for (int i = x - 1; i < x + 2; i++) {
            if (i >= 0 && i < fieldSizeY) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (j >= 0 && j < fieldSizeX) {
                        if (fieldWithAnswer[i][j] == 'X') {
                            amountOfMines++;
                        }
                    }
                }
            }
        }
        return amountOfMines;
    }

    public void fillFieldWithAmountOfMinesAround() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (fieldWithAnswer[i][j] != 'X') {
                    int amountOfMinesAround = checkMinesAround(i, j);
                    if (amountOfMinesAround > 0) {
                        fieldWithAnswer[i][j] = (char) (amountOfMinesAround + '0');
                    }
                }
            }
        }
    }

    public void markCellAsMined(int x, int y) {
        if (x < 0 && x > fieldSizeX) {
            System.out.println("Error x coordinate");
            return;
        }
        if (y < 0 && y > fieldSizeY) {
            System.out.println("Error y coordinate");
            return;
        }
        if (fieldInGame[y][x] == '.') {
            fieldInGame[y][x] = '*';
        } else if (fieldInGame[y][x] == '*') {
            fieldInGame[y][x] = '.';

        } else {
            System.out.println("There is a number here!");
        }

    }


    public boolean isGameRunning() {
        if (isSteppedOnMine) {
            System.out.println("You stepped on a mine and failed!");
            return false;
        }
        int amountOfCorrectCheckedMines = 0;
        int amountOfUncheckedMines = 0;
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (fieldInGame[i][j] == '*' && fieldWithAnswer[i][j] == 'X') {
                    amountOfCorrectCheckedMines++;
                }
            }
        }
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (fieldInGame[i][j] == '/') {
                    amountOfUncheckedMines++;
                }
            }
        }
        if (amountOfMines == amountOfCorrectCheckedMines ||
                amountOfMines == fieldSizeX * fieldSizeY - amountOfUncheckedMines) {
            System.out.println("Congratulations! You found all the mines!");
            return false;
        }

        return true;
    }

    public void claimCellAsFree(int x, int y) {
        if (fieldWithAnswer[y][x] == 'X') {
            isSteppedOnMine = true;
            for (int i = 0; i < fieldSizeY; i++) {
                for (int j = 0; j < fieldSizeX; j++) {
                    if (fieldInGame[i][j] == '.' && fieldWithAnswer[i][j] == 'X') {
                        fieldInGame[i][j] = 'X';
                    }
                }
            }
            return;
        }

        arrayDequeColumnToCheck.push(x);
        arrayDequeRowToCheck.push(y);
        while (!arrayDequeColumnToCheck.isEmpty() && !arrayDequeRowToCheck.isEmpty()) {
            checkAndMarkEmptyCellsInRow(arrayDequeColumnToCheck.pop(), arrayDequeRowToCheck.pop());
        }
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (fieldInGame[i][j] == '/') {
                    markCellAroundEmpty(i, j);
                }
            }
        }
    }

    private void checkAndMarkEmptyCellsInRow(int x, int y) {
        for (int i = x; i < fieldSizeX; i++) {

            if (fieldInGame[y][i] == '.' && fieldWithAnswer[y][i] == '.') {
                fieldInGame[y][i] = '/';
                checkUpDownCells(i, y);
                fieldWithAnswer[y][i] = '/';
            } else if (fieldWithAnswer[y][i] == '.' && fieldInGame[y][i] == '*') {
                fieldInGame[y][i] = '/';
                checkUpDownCells(i, y);
                fieldWithAnswer[y][i] = '/';
            } else if (fieldWithAnswer[y][i] != 'X'
                    && fieldWithAnswer[y][i] != '.') {
                fieldInGame[y][i] = fieldWithAnswer[y][i];
                break;
            }
        }

        for (int i = x - 1; i >= 0; i--) {
            if (fieldInGame[y][i] == '.' && fieldWithAnswer[y][i] == '.') {
                fieldInGame[y][i] = '/';
                checkUpDownCells(i, y);
                fieldWithAnswer[y][i] = '/';
            } else if (fieldWithAnswer[y][i] == '.' && fieldInGame[y][i] == '*') {
                fieldInGame[y][i] = '/';
                checkUpDownCells(i, y);
                fieldWithAnswer[y][i] = '/';
            } else if (fieldWithAnswer[y][i] != 'X'
                    && fieldWithAnswer[y][i] != '.') {
                fieldInGame[y][i] = fieldWithAnswer[y][i];
                break;
            }
        }

    }

    private void checkUpDownCells(int x, int y) {
        if (y + 1 < fieldSizeY) {
            arrayDequeColumnToCheck.push(x);
            arrayDequeRowToCheck.push(y + 1);
        }
        if (y - 1 >= 0) {
            arrayDequeColumnToCheck.push(x);
            arrayDequeRowToCheck.push(y - 1);
        }
    }

    private void markCellAroundEmpty(int x, int y) {
        for (int i = x - 1; i < x + 2; i++) {
            if (i >= 0 && i < fieldSizeY) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (j >= 0 && j < fieldSizeX) {

                        if (fieldWithAnswer[i][j] != 'X') {
                            fieldInGame[i][j] = fieldWithAnswer[i][j];
                        }
                    }
                }
            }
        }
    }
}
