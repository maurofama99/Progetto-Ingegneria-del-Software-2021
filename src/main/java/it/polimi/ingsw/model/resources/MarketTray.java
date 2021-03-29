package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class MarketTray {
    private Resource[][] tray;
    private Resource slide;

    public MarketTray(Resource[][] tray, Resource slide) {
        this.tray = tray;
        this.slide = slide;
    }

    public Resource[][] getTray() {
        return tray;
    }

    public void setSlide(Resource slide) {
        this.slide = slide;
    }

    public Resource getSlide() {
        return slide;
    }

    //restituisce un arraylist con le risorse della riga selezionata e usa lo slide.
    //L'arraylist restituito è composto dalle risorse selezionate con quantià = 1,
    //restituisce anche white resource che va ignorata al momento del deposito.
    //Lo stesso vale per selectColumn
    public ArrayList<Resource> selectRow(int row) throws IndexOutOfBoundsException{
        ArrayList<Resource> resources;
        Resource[][] tmptray = new Resource[3][4];
        int i;

        //ricopio in tmptray il tray iniziale
        for (i=0; i<3; i++){
            tmptray[i] = (tray[i].clone());
        }

        if (row == 1){
            //aggiungi le risorse della riga all'array da restituire
            resources = new ArrayList<>(Arrays.asList(tray[0]));

           //cambia la disposizione del tray a seguito dello slide
            System.arraycopy(tmptray[0], 0, tray[0], 1, 3);
            tray[0][0] = slide;

            //setta il nuovo slide
            this.slide = resources.get(resources.size()-1);
        }

        else if (row == 2) {

            resources = new ArrayList<>(Arrays.asList(tray[1]));

            System.arraycopy(tmptray[1], 0, tray[1], 1, 3);
            this.tray[1][0]=slide;

            this.setSlide(resources.get(resources.size()-1));
        }

        else if (row == 3){
            resources = new ArrayList<>(Arrays.asList(tray[2]));

            System.arraycopy(tmptray[2], 0, tray[2], 1, 3);
            tray[2][0]=slide;

            this.slide = resources.get(resources.size()-1);
        }

        else throw new IndexOutOfBoundsException();

        return resources;
    }

    //restituisce un arraylist con le risorse della colonna selezionata
    public ArrayList<Resource> selectColumn(int col){
        ArrayList<Resource> resources= new ArrayList<>();
        Resource[][] tmptray = new Resource[3][4];
        int i;

        for (i=0; i<3; i++){
            tmptray[i] = (tray[i].clone());  //ricopio in tmptray il tray iniziale
        }

        if (col == 1){
            for (i=0; i<3; i++){
                resources.add(tray[i][0]);
            }

            for (i=1; i<3; i++){
                tray[i][0] = tmptray[i-1][0];
            }

            tray[0][0] = slide;
            this.slide = resources.get(resources.size()-1);
        }


        else if (col == 2) {
            for (i=0; i<3; i++){
                resources.add(tray[i][1]);
            }


            for (i=1; i<3; i++){
                tray[i][1] = tmptray[i-1][1];
            }
            tray[0][1] = slide;
            this.slide = resources.get(resources.size()-1);
        }


        else if (col == 3){
            for (i=0; i<3; i++){
                resources.add(tray[i][2]);
            }


            for (i=1; i<3; i++){
                tray[i][2] = tmptray[i-1][2];
            }
            tray[0][2] = slide;
            this.slide = resources.get(resources.size()-1);
        }


        else if (col == 4){
            for (i=0; i<3; i++){
                resources.add(tray[i][3]);
            }


            for (i=1; i<3; i++){
                tray[i][3] = tmptray[i-1][3];
            }
            tray[0][3] = slide;
            this.slide = resources.get(resources.size()-1);
        }


        else throw new IndexOutOfBoundsException();

        return resources;
    }


    //aggiunge le risorse dell'arraylist "resources" al player "player"
    public void giveResources(Player player, ArrayList<Resource> resources){
    }
}
