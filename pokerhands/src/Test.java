import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws Exception {
        File file = new File("./pokerhands/pokerhands/src/test.txt");
        Scanner scanner = new Scanner(file);

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
        System.out.println(hand1.getHand());

        Hand hand2 = new Hand(hand2Cards);
        System.out.println(hand2.getHand());

        scanner.close();
    }
}
