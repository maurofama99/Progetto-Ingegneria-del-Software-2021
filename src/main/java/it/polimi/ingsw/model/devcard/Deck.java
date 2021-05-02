package it.polimi.ingsw.model.devcard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Class of dev. cards' deck.
 */
public class Deck {

    private Stack<DevCard>[][] fullDeck = new Stack[3][4];
    private ArrayList<DevCard> generalDeck;


    //when Deck class is created, generalDeck is already setted with all the devCards
    public Deck() {
        Gson gson = new Gson();
        int count = 0;

        try (Reader reader = new FileReader("src/main/resources/DevelopmentCards.json")) {

            Type devCardListType = new TypeToken<ArrayList<DevCard>>() {
            }.getType();

            generalDeck = gson.fromJson(reader, devCardListType);

        } catch (IOException e) {
            System.out.println("Can't find DevelopmentCards.json path");
            e.printStackTrace();
        }

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


    /**
     * This method is literally a getter of the deck on the table
     *
     * @return the matrix of the cards still on the table (not taken by players or removed)
     */
    public DevCard[][] ShowedCards() {

        DevCard[][] result = new DevCard[3][4];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = fullDeck[i][j].peek();
            }
        }
        return result;
    }

    /**
     * Returns a single card to check for its parameters
     *
     * @param row    row selected (starts from 1)
     * @param column column selected (starts from 1)
     * @return the card selected if present
     */
    public DevCard getDevCard(int row, int column) {
        return fullDeck[row - 1][column - 1].peek();
    }

    /**
     * This method will be used for buying and removing card with tokens.
     *
     * @param row row of the deck, from one (more intuitive)
     * @param col column of the deck, from one (more intuitive)
     * @return a pop of the deck, removing the selected card (the top one)
     */
    public DevCard removeAndGetCard(int row, int col) {
        return fullDeck[row - 1][col - 1].pop();
    }

}
