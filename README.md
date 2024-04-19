# Poker Hands - Project Euler Problem #54 Solution

Explanation:
The solution makes use of a Card class, used to represent individual cards in a hand, and a Hand class, used to represent individual hands (five cards each).
To compare two hands, the Hand class implements the Comparable interface and the compareTo method. 
When comparing, the type of hand (pair, three of a kind, etc.) is determined. This is done through an if-else tree, checking the highest ranking hands in order (royal flush down to high card). 
For the pairs, three of a kind, and four of a kind hands, a hash map is used to store the count for each symbol (ace, two, king, etc.). Using this map, the corresponding methods then check the hand's card count map to see if the given hand is achieved. If both players have the same type of hand (ex: both have three of a kind), the rank made up of the highest value is determined to properly identify the winner. To do so, helper methods were created to compare specific types of hands against each other, abstracting code whenever possible.

What I like:
I like that the solution is modular, dividing the Hand class into small focused methods, enhancing readibility and making it easy to extend. 

What I don't like:
The solution does not include error handling when comparing hands, which limits its robustness. However, this was done as the task specifies that all hands are valid.

New technologies/approaches:
While none of the concepts or technologies were new to me, I had not programmed in Java in a two years and it took some time to re-acclimate to Java-specifc syntax and methodology. 
