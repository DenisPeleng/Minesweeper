package minesweeper;

import java.util.Random;

public class FieldMinesweeper {
    int fieldSize;
    char[][] field;
    int amountOfMines;

    FieldMinesweeper(int fieldSize) {
        this.fieldSize = fieldSize;
        this.field = createField(fieldSize);
    }

    public char[][] createField(int fieldSize) {
        char[][] field = new char[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
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
            int randomX = random.nextInt(fieldSize);
            int randomY = random.nextInt(fieldSize);
            if (field[randomX][randomY] != 'X') {
                field[randomX][randomY] = 'X';
                currentAmountOfMines++;
            }
        }
    }

    public void printField() {

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }

    private int checkMinesAround(int x, int y) {
        int amountOfMines = 0;
        for (int i = x - 1; i < x + 2; i++) {
            if (i >= 0 && i < fieldSize) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (j >= 0 && j < fieldSize) {
                        if (field[i][j] == 'X') {
                            amountOfMines++;
                        }
                    }
                }
            }
        }
        return amountOfMines;
    }

    public void fillFieldWithAmountOfMines() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (field[i][j] != 'X') {
                    int amountOfMinesAround = checkMinesAround(i, j);
                    if (amountOfMinesAround > 0) {
                        field[i][j] = (char) (amountOfMinesAround + '0');
                    }
                }
            }
        }
    }
}
