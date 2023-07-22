package sk.tuke.gamestudio.game.maze;

import java.util.Scanner;

public class Regex {
    Scanner scanner = new Scanner(System.in);
    public int menuInput(){
         String wasd1 = scanner.nextLine();
        if (wasd1.matches("^1")
                |wasd1.matches("^2")
                |wasd1.matches("^3")
                |wasd1.matches("^4")
                |wasd1.matches("^5")
                |wasd1.matches("^0")
                |wasd1.matches("^6")
                |wasd1.matches("^7")
                |wasd1.matches("^8")
        ) {

            return getCharFromString(wasd1,0)-48;
        }else{
            System.out.println("Incorrect input.");
            System.out.println("Try again");
        }
        return -1;
    }
    public static char getCharFromString(String str, int index)
    {
        return str.charAt(index);
    }
}
