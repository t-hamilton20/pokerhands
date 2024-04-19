import java.util.Collections;
import java.util.List;

public class Hand {
    private List<Card> cards;

    public Hand(List<Card> cards){
        this.cards = cards;
        Collections.sort(this.cards);
    }

    public List<Card> getHand(){
        return this.cards;
    }
}
