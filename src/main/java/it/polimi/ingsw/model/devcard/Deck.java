package it.polimi.ingsw.model.devcard;

import java.util.Stack;

/**
 * Class of dev. cards' deck.
*/
public class Deck {

    /**
     The arraylist is used as a stack of cards. The last one is the showed card
     */
    Stack<DevCard>[][] fullDeck;

    //inizializzazione del deck con gson

    /**
     * This method is literally a getter of the deck on the table
     * @return the matrix of the cards still on the table (not taken by players or removed)
     */
    public DevCard[][] ShowedDeck(){

        DevCard[][] result = new DevCard[3][4];

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                result[i][j] = fullDeck[i][j].peek();
            }
        }
        return result;
    }

    /**
     * This method will be used for buying and removing card with tokens.
     * @param row row of the deck, from one (more intuitive)
     * @param col column of the deck, from one (more intuitive)
     * @return a pop of the deck, removing the selected card (the top one)
     */
    //rimuove e restituisce la carta selezionata tra quelle mostrate sul tavolo
    //indici partono da 1 per l'utente (più intuitivo)
    public DevCard removeAndGetCard(int row, int col){
        return fullDeck[row-1][col-1].pop();
    }

}
