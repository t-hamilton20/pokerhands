import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Hand implements Comparable<Hand> {
    private List<Card> cards;
    private HashMap<Character, Integer> cardCounts = new HashMap<Character,Integer>();

    public Hand(List<Card> cards){
        this.cards = cards;
        Collections.sort(this.cards);
        this.cardCounts = this.checkCardCounts();
    }

    public List<Card> getHand(){
        return this.cards;
    }

    public String toString(){
        return this.getHand().toString();
    }

    public HashMap<Character,Integer> getCardCounts(){
        return new HashMap<Character,Integer>(cardCounts);
    }

    public boolean checkRoyalFlush(){
        int count = 0;
        for (Card card: this.getHand()){
            if (card.getSymbolNumeric() != count+10){
                return false;
            }
            count++;
        }

        return checkStraightFlush();
    }

    public boolean checkStraightFlush(){
        return this.checkFlush() && this.checkStraight();
    }

    public HashMap<Character, Integer> checkCardCounts(){
        HashMap<Character, Integer> cardCounts = new HashMap<Character,Integer>();

        for (Card card: this.getHand()){
            char symbol = card.getSymbol();
            int count = cardCounts.getOrDefault(symbol, 0) + 1;
            cardCounts.put(symbol, count);
        }

        return cardCounts;
    }

    public boolean checkFourOfAKind() {
        for (int count : cardCounts.values()) {
            if (count == 4) {
                return true;
            }
        }
        return false;
    }

    public boolean checkFullHouse() {
        for (int count : cardCounts.values()) {
            if (count == 4) {
                return true;
            }
        }
        return false;
    }

    public boolean checkFlush(){
        char firstSuit = this.cards.get(0).getSuit();
        for (int i = 1; i < this.cards.size(); i++){
            if (this.cards.get(i).getSuit() != firstSuit){
                return false;
            }
        }
        return true;
    }

    public boolean checkStraight(){        
        List<Card> cards = this.getHand();
        int firstSymbol = cards.get(0).getSymbolNumeric();

        if (cards.get(4).getSymbol() == 'A' && cards.get(3).getSymbol() != 'K'){ 
            // handle ace, 2, 3, 4, 5 case
            for (int i = 0; i < cards.size() - 1; i++){
                if (cards.get(i).getSymbolNumeric() != (firstSymbol + i)){
                    return false;
                }
            }
        }

        else {
            for (int i = 1; i < cards.size(); i++){
                if (cards.get(i).getSymbolNumeric() != (firstSymbol + i)){
                    return false;
                }
            }
        } 

        return true;
    }

    public boolean checkThreeOfAKind() {
        for (int count : cardCounts.values()) {
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTwoPairs() {
        int pairCount = 0;
    
        for (int count : cardCounts.values()) {
            if (count == 2) {
                pairCount++;
            }
        }
    
        return pairCount == 2;
    }
    

    public boolean checkPair() {
        for (int count : cardCounts.values()) {
            if (count == 2) {
                return true;
            }
        }
        return false;
    }

    public int compareTo(Hand otherHand){
        int handStrength = this.getHandStrength();
        int otherHandStrength = otherHand.getHandStrength();

        if (handStrength != otherHandStrength){ // hands are not the same
            return Integer.compare(handStrength, otherHandStrength);
        }

        else { // hands are the same, must check highest cards

            // do not need to check royal flush, only one player can have it at once
            // default compare high cards
            // only need to handle cases with pairs/three/four of a kind
            // for straights and flushes, high card is tie-breaker

            if(handStrength == HandType.get("FOUR_OF_A_KIND")){
                char thisSymbol = findLikeCards(this, 4, -1);
                char otherSymbol = findLikeCards(otherHand, 4, -1);

                int result = Integer.compare(Card.symbolToNumeric.get(thisSymbol), Card.symbolToNumeric.get(otherSymbol));
                if (result == 0){ // same four of a kind, compare other card
                    return this.compareHighCard(otherHand);
                } 
                return result;
            }

            else if (handStrength == HandType.get("FULL_HOUSE")){
                int threeOfAKindCompare = compareThreeOfAKind(otherHand, true);
                if (threeOfAKindCompare == 0){ // same three of a kind, compare pair
                    return compareOnePair(otherHand);
                }
                return threeOfAKindCompare;
            }

            else if (handStrength == HandType.get("THREE_OF_A_KIND")){
                return compareThreeOfAKind(otherHand, false);
            }

            else if (handStrength == HandType.get("TWO_PAIRS")){
                char thisSymbol1 = findLikeCards(this, 2, -1);
                char otherSymbol1 = findLikeCards(otherHand, 2, -1);
                int result = Integer.compare(Card.symbolToNumeric.get(thisSymbol1), Card.symbolToNumeric.get(otherSymbol1));

                if (result == 0){ // same high pair, compare next pair

                    char thisSymbol2 = findLikeCards(this, 2, thisSymbol1);
                    char otherSymbol2 = findLikeCards(otherHand, 2, thisSymbol2);
                    result = Integer.compare(Card.symbolToNumeric.get(thisSymbol2), Card.symbolToNumeric.get(otherSymbol2));

                    if (result == 0){ // same set of pairs, compare other card
                        return this.compareHighCard(otherHand);
                    }
                } 
                return result;
            }                    
                    
            else if(handStrength == HandType.get("ONE_PAIR")){
                return compareOnePair(otherHand);
            }

            else{
                return this.compareHighCard(otherHand);
            }        
        }
    }

    private int compareThreeOfAKind(Hand otherHand, boolean fullHouse){
        char thisSymbol = findLikeCards(this, 3, -1);
        char otherSymbol = findLikeCards(otherHand, 3, -1);

        int result = Integer.compare(Card.symbolToNumeric.get(thisSymbol), Card.symbolToNumeric.get(otherSymbol));
        if (result == 0 && !fullHouse){ // compare high cards if symbols are the same and not part of full house
            return this.compareHighCard(otherHand);
        } 
        return result;
    }

    private int compareOnePair(Hand otherHand){
        char thisSymbol = findLikeCards(this, 2, -1);
        char otherSymbol = findLikeCards(otherHand, 2, -1);

        int result = Integer.compare(Card.symbolToNumeric.get(thisSymbol), Card.symbolToNumeric.get(otherSymbol));
        if (result == 0){ 
            return this.compareHighCard(otherHand);
        } 
        return result;
    }

    private char findLikeCards(Hand hand, int targetValue, int excludeSymbol){
        // used to find pairs and 3/4 of a kind
        // exclude symbol is used in finding two pairs

        for (HashMap.Entry<Character, Integer> entry : hand.cardCounts.entrySet()) {
            if (entry.getValue() == targetValue && entry.getKey() != excludeSymbol){
                return entry.getKey();
            }
        }
        return 'e'; // should never be returned
    }
    

    private int compareHighCard(Hand otherHand){
        // cards are sorted already
        // check highest card, move onto next if cards have same symbol

        List<Card> thisCards = this.getHand();
        List<Card> otherCards = otherHand.getHand();
        
        for (int i = thisCards.size() - 1; i >= 0; i--) {
            int thisSymbol = thisCards.get(i).getSymbolNumeric();
            int otherSymbol = otherCards.get(i).getSymbolNumeric();
            
            if (thisSymbol != otherSymbol) {
                return Integer.compare(thisSymbol, otherSymbol);
            }
        }
        return 0; 
    }

    public int getHandStrength(){
        if (this.checkRoyalFlush()){
            return HandType.get("ROYAL_FLUSH");
        }
        else if (this.checkStraightFlush()){
            return HandType.get("STRAIGHT_FLUSH");
        } 

        else if (this.checkFourOfAKind()){
            return HandType.get("FOUR_OF_A_KIND");
        } 

        else if (this.checkFullHouse()){
            return HandType.get("FULL_HOUSE");
        }

        else if (this.checkFlush()){
            return HandType.get("FLUSH");
        }

        else if (this.checkStraight()){
            return HandType.get("STRAIGHT");
        }

        else if (this.checkThreeOfAKind()){
            return HandType.get("THREE_OF_A_KIND");
        }

        else if (this.checkTwoPairs()){
            return HandType.get("TWO_PAIRS");
        }

        else if (this.checkPair()){
            return HandType.get("ONE_PAIR");
        }

        else{
            return HandType.get("HIGH_CARD");
        }
    }

    public static final HashMap<String, Integer> HandType; // map used to rank hands 
    static {
        HandType = new HashMap<String, Integer>();
        HandType.put("HIGH_CARD", 1);
        HandType.put("ONE_PAIR", 2);
        HandType.put("TWO_PAIRS", 3);
        HandType.put("THREE_OF_A_KIND", 4);
        HandType.put("STRAIGHT", 5);
        HandType.put("FLUSH", 6);
        HandType.put("FULL_HOUSE", 7);
        HandType.put("FOUR_OF_A_KIND", 8);
        HandType.put("STRAIGHT_FLUSH", 9);
        HandType.put("ROYAL_FLUSH", 10);
    }
}
