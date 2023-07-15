package nimgame;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int packsNum = 0;
        List <Integer> packs = new ArrayList<> ();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of packs: ");
        packsNum = scanner.nextInt();
        
        for(int i=1; i<packsNum+1; ++i) {
            System.out.print("Enter the number of sticks in pack#" + i + ": ");
            packs.add(scanner.nextInt());
        }
        
        Game game = new Game(packs);
        game.launch();
        
        System.gc();
        System.exit(0);
    }

}
