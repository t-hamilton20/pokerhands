import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws Exception {
        File file = new File("./pokerhands/pokerhands/src/hands.txt");
        Scanner scanner = new Scanner(file);

        int player1Count = 0; 
        int player2Count = 0;

        while (scanner.hasNextLine()){
            String[] cards = scanner.nextLine().split(" ");
                    
            List<Card> hand1Cards = new ArrayList<Card>();
            List<Card> hand2Cards = new ArrayList<Card>();
            
            for (int i = 0; i < 10; i++) {
                Card card = new Card(cards[i]);
                if (i < 5) {
                    hand1Cards.add(card);
                } else {
                    hand2Cards.add(card);
                }
            }
            
            Hand hand1 = new Hand(hand1Cards);
            Hand hand2 = new Hand(hand2Cards);

            if (hand1.compareTo(hand2) > 0){
                player1Count++;
            } 
            else{
                player2Count++;
            }
        }

        System.out.println("Player 1 wins: " + player1Count);
        System.out.println("Player 2 wins: " + player2Count);

        scanner.close();
    }
}
