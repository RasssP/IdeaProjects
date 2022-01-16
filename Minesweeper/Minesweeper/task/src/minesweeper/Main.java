package minesweeper;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String qTotalMines = "How many mines do you want on the field?";
        System.out.println(qTotalMines);
        int totalMines = scanner.nextInt();
        MineSweeper mineSweeper = new MineSweeper(totalMines);
        mineSweeper.play();
    }

}
