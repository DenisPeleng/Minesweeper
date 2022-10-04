package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int fieldSize = 9;
        Scanner scanner = new Scanner(System.in);
        FieldMinesweeper fieldMinesweeper = new FieldMinesweeper(fieldSize);
        System.out.println("How many mines do you want on the field?");
        int amountOfMines = scanner.nextInt();
        fieldMinesweeper.fillMinesOnField(amountOfMines);
        fieldMinesweeper.fillFieldWithAmountOfMines();
        fieldMinesweeper.printField();
    }


}
