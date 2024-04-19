import java.util.HashMap;

public class Card implements Comparable<Card> {
    private final char symbol;
    private final char suit;

    public Card(char symbol, char suit){
        this.symbol = symbol;
        this.suit = suit;
    }

    public Card(String input){
        this.symbol = input.charAt(0);
        this.suit = input.charAt(1);
    }

    private static final HashMap<Character, Integer> symbolToNumeric;
    static {
        symbolToNumeric = new HashMap<Character, Integer>();
        symbolToNumeric.put('2', 2);
        symbolToNumeric.put('3', 3);
        symbolToNumeric.put('4', 4);
        symbolToNumeric.put('5', 5);
        symbolToNumeric.put('6', 6);
        symbolToNumeric.put('7', 7);
        symbolToNumeric.put('8', 8);
        symbolToNumeric.put('9', 9);
        symbolToNumeric.put('T', 10);
        symbolToNumeric.put('J', 11);
        symbolToNumeric.put('Q', 12);
        symbolToNumeric.put('K', 13);
        symbolToNumeric.put('A', 14);
    }

    private static final HashMap<Character, String> symbolToString;
    static {
        symbolToString = new HashMap<Character, String>();
        symbolToString.put('T', "Ten");
        symbolToString.put('J', "Jack");
        symbolToString.put('Q', "Queen");
        symbolToString.put('K', "King");
        symbolToString.put('A', "Ace");
    }

    private static final HashMap<Character, String> suitToString;
    static {
        suitToString = new HashMap<Character, String>();
        suitToString.put('S', "Spades");
        suitToString.put('C', "Clubs");
        suitToString.put('H', "Hearts");
        suitToString.put('D', "Diamonds");
    }


    public int compareTo(Card otherCard){
        int thisSymbol = symbolToNumeric.get(this.symbol);
        int otherSymbol = symbolToNumeric.get(otherCard.symbol);

        return Integer.compare(thisSymbol, otherSymbol);
    }

    public char getSymbol(){
        return this.symbol;
    }

    public int getSymbolNumeric(){
        return symbolToNumeric.get(this.symbol);
    }

    public char getSuit(){
        return this.suit;
    }

    public String toString(){
        String symbolStr = String.valueOf(this.getSymbol());

        if (this.getSymbolNumeric() > 9){
            symbolStr = symbolToString.get(this.getSymbol());
        }

        String msg = symbolStr + " of " + suitToString.get(this.getSuit());
        return msg;
    }

}


