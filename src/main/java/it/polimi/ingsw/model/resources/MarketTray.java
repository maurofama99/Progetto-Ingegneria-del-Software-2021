package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class MarketTray {
    private Resource[][] tray = new Resource[2][3];
    private Resource slide;

    //restituisce un arraylist con le risorse della riga selezionata
    //public ArrayList<Resource> selectRow(int row){};

    //restituisce un arraylist con le risorse della colonna selezionata
    //public ArrayList<Resource> selectColumn(int col){};

    //riposiziona le risorse nel mercato in seguito allo slide
    public void useSlide(){}

    //aggiunge le risorse "resources" al player "player"
    public void giveResources(Player player, ArrayList<Resource> resources){}
}
