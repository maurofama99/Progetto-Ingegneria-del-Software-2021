package it.polimi.ingsw.model.devcard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Class of development cards' deck.
 */
public class Deck implements Serializable {

    private static final long serialVersionUID = -4782394927810413440L;

    private final Stack<DevCard>[][] fullDeck = new Stack[3][4];
    private ArrayList<DevCard> generalDeck;


    /**
     * Deck contains all the development cards and the constructor uses a json file to create the card
     */
    public Deck() {
        Gson gson = new Gson();
        int count = 0;

        Type devCardListType = new TypeToken<ArrayList<DevCard>>() {
            }.getType();

        generalDeck = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/DevelopmentCards.json")), devCardListType);



        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                fullDeck[i][j] = new Stack<>();
                for (int k = 0; k < 4; k++) {
                    fullDeck[i][j].push(generalDeck.get(count));
                    count++;
                }
                Collections.shuffle(fullDeck[i][j]);
            }
        }

    }

    public Stack<DevCard>[][] getFullDeck() {
        return fullDeck;
    }

    /**
     * This method is literally a getter of the deck on the table
     * @return the matrix of the cards still on the table (not taken by players or removed)
     */
    public DevCard[][] showedCards() {

        DevCard[][] result = new DevCard[3][4];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (fullDeck[i][j].empty())
                    result [i][j] =null;

                else result[i][j] = fullDeck[i][j].peek();
            }
        }
        return result;
    }

    /**
     * Returns a single card to check for its parameters
     * @param row    row selected (starts from 1)
     * @param column column selected (starts from 1)
     * @return the card selected if present, if not present returns null.
     */
    public DevCard getDevCard(int row, int column) {
        if (!fullDeck[row-1][column-1].empty())
            return fullDeck[row - 1][column - 1].peek();
        else return null;
    }

    /**
     * This method will be used for buying and removing card with tokens.
     *
     * @param row row of the deck, from one (more intuitive)
     * @param col column of the deck, from one (more intuitive)
     */
    public void removeAndGetCard(int row, int col) {
        if (!fullDeck[row-1][col-1].empty()) {
            fullDeck[row - 1][col - 1].pop();
        }
    }


}
