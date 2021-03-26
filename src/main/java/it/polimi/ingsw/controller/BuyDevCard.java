package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.devcard.DevCard;

public class BuyDevCard implements TableState{
    @Override
    public void doAction() {

    }

    public void isLegal(){}

    //restituisce la Dev Card in posizione row-col della matrice sul table
    //public DevCard extractCard(int row, int col){    }

    public void manageResources(){}

    //metti la carta comprata nel primo slot libero
    public void placeCard(DevCard card){}

    //posiziona una nuova carta nello slot selezionato precedentemente (quello della carta comprata)
    public void removeAndReplaceCard(int row, int col){}


}
