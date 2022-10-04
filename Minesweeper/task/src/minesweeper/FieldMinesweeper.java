package minesweeper;

import java.util.Random;

public class FieldMinesweeper {
    private final int fieldSizeX;
    private final int fieldSizeY;
    private final char[][] fieldInGame;
    private final char[][] fieldWithAnswer;
    private int amountOfMines;

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

    public void fillMinesOnField(int amountOfMines) {
        Random random = new Random();
        int currentAmountOfMines = 0;
        this.amountOfMines = amountOfMines;
        while (currentAmountOfMines != amountOfMines) {
            int randomX = random.nextInt(fieldSizeX);
            int randomY = random.nextInt(fieldSizeY);
            if (fieldWithAnswer[randomY][randomX] != 'X') {
                fieldWithAnswer[randomY][randomX] = 'X';
                currentAmountOfMines++;
            }
        }
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

    public void fillFieldWithAmountOfMines() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (fieldWithAnswer[i][j] != 'X') {
                    int amountOfMinesAround = checkMinesAround(i, j);
                    if (amountOfMinesAround > 0) {
                        fieldInGame[i][j] = (char) (amountOfMinesAround + '0');
                    }
                }
            }
        }
    }

    public void fillPlayerTurn(int x, int y) {
        if (x <= 0 && x < fieldSizeX) {
            System.out.println("Error x coordinate");
            return;
        }
        if (y <= 0 && x < fieldSizeY) {
            System.out.println("Error y coordinate");
            return;
        }
        x = x - 1;
        y = y - 1;
        if (fieldInGame[y][x] == '.') {
            fieldInGame[y][x] = '*';
        } else if (fieldInGame[y][x] == '*') {
            fieldInGame[y][x] = '.';

        } else {
            System.out.println("There is a number here!");
        }

    }


    public boolean isUserWin() {
        int amountOfCorrectCheckedMines = 0;
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (fieldInGame[i][j] == '*' && fieldWithAnswer[i][j] == 'X') {
                    amountOfCorrectCheckedMines++;
                }
            }
        }
        if (amountOfMines == amountOfCorrectCheckedMines) {
            System.out.println("Congratulations! You found all the mines!");
            return true;
        }
        return false;
    }
}
