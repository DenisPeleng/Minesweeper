package minesweeper;

import java.util.Scanner;

public class Main {
    static boolean isGameRun = true;

    public static void main(String[] args) {
        int fieldSizeX = 9;
        int fieldSizeY = 9;
        Scanner scanner = new Scanner(System.in);
        FieldMinesweeper fieldMinesweeper = new FieldMinesweeper(fieldSizeX, fieldSizeY);
        System.out.println("How many mines do you want on the field?");
        int amountOfMines = scanner.nextInt();
        fieldMinesweeper.fillMinesOnField(amountOfMines);
        fieldMinesweeper.fillFieldWithAmountOfMines();
        fieldMinesweeper.printField();
        while (isGameRun) {
            System.out.println("Set/delete mines marks (x and y coordinates): ");
            int currentX = scanner.nextInt();
            int currentY = scanner.nextInt();
            fieldMinesweeper.fillPlayerTurn(currentX, currentY);
            fieldMinesweeper.printField();
            isGameRun = !fieldMinesweeper.isUserWin();

        }
    }


}
