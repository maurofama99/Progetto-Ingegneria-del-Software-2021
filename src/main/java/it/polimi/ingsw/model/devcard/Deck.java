package it.polimi.ingsw.model.devcard;

import java.util.Stack;

public class Deck {

    //l'arraylist viene usato come pila di carte: l'ultima carta dell'arraylist è la showed card
    Stack<DevCard>[][] fullDeck;

    //inizializzazione del deck con gson

    //ritorna la matrice di carte mostrate sul tavolo
    public DevCard[][] ShowedDeck(){

        DevCard[][] result = new DevCard[3][4];

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                result[i][j] = fullDeck[i][j].peek();
            }
        }
        return result;
    }

    //rimuove e restituisce la carta selezionata tra quelle mostrate sul tavolo
    //indici partono da 1 per l'utente (piùà intuitivo)
    public DevCard removeAndGetCard(int row, int col){
        return fullDeck[row-1][col-1].pop();
    }

}
