package minesweeper;

import java.util.Scanner;

public class Main {
    static boolean isGameRun = true;

    public static void main(String[] args) {
        int fieldSizeX = 9;
        int fieldSizeY = 9;
        boolean isCorrectAmountOfMines = false;
        boolean isFirstTurn = true;
        int amountOfMines;
        Scanner scanner = new Scanner(System.in);

        FieldMinesweeper fieldMinesweeper = new FieldMinesweeper(fieldSizeX, fieldSizeY);

        while (!isCorrectAmountOfMines) {
            System.out.println("How many mines do you want on the field?");
            amountOfMines = scanner.nextInt();
            fieldMinesweeper.setAmountOfMines(amountOfMines);
            if (amountOfMines < fieldSizeX * fieldSizeY) {
                isCorrectAmountOfMines = true;
            } else {
                System.out.println("Error, choose smaller amount of mines");
            }
        }

        fieldMinesweeper.printField();
        while (isGameRun) {
            System.out.println("Set/unset mine marks or claim a cell as free:");
            int currentX = scanner.nextInt() - 1;
            int currentY = scanner.nextInt() - 1;
            String action = scanner.next();
            switch (action) {
                case "free" -> {
                    if (isFirstTurn) {
                        fieldMinesweeper.fillMinesOnField(currentX, currentY);
                        isFirstTurn = false;
                    }
                    fieldMinesweeper.claimCellAsFree(currentX, currentY);
                    fieldMinesweeper.printField();
                    isGameRun = fieldMinesweeper.isGameRunning();
                }
                case "mine" -> {
                    fieldMinesweeper.markCellAsMined(currentX, currentY);
                    fieldMinesweeper.printField();
                    isGameRun = fieldMinesweeper.isGameRunning();
                }
                default -> System.out.println("Wrong command");
            }


        }
    }


}
