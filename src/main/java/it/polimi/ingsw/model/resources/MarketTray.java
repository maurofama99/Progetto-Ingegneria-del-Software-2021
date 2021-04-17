package it.polimi.ingsw.model.resources;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Where the marbles are placed and players take resources
 */
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


    /**
     * This method is called when a player selects a row.
     * @param row which row of the market is selected
     * @return an arraylist of resources, white marbles are ignored when it is time to store
     * @throws IndexOutOfBoundsException if the int is >3
     */
    public ArrayList<Resource> selectRow(int row) throws IndexOutOfBoundsException{
        ArrayList<Resource> resources;
        Resource[][] tmptray = new Resource[3][4];
        int i;

        //copying in tmptray the initial tray
        for (i=0; i<3; i++){
            tmptray[i] = (tray[i].clone());
        }

        if (row>=1 && row<=3) {
            //adding row's resources in the array to be returned
            resources = new ArrayList<>(Arrays.asList(tray[row - 1]));

            //changes the tray disposition after using the slide
            System.arraycopy(tmptray[row - 1], 0, tray[row - 1], 1, 3);
            tray[row - 1][0] = slide;

            //sets the new slide
            this.slide = resources.get(resources.size() - 1);
        } else throw new IndexOutOfBoundsException();

        //remove all white resources from the array
        //resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE)); //uses iterator
        return resources;
    }

    /**
     * This method is called when a player selects a column.
     * @param col which column of the market is selected
     * @return an arraylist of resources, white marbles are ignored when it is time to store
     * @throws IndexOutOfBoundsException if the int is >4
     */
    public ArrayList<Resource> selectColumn(int col){
        ArrayList<Resource> resources= new ArrayList<>();
        Resource[][] tmptray = new Resource[3][4];
        int i;

        for (i=0; i<3; i++){
            tmptray[i] = (tray[i].clone());  //copying in tmptray the initial tray
        }

        if (col >= 1 && col <= 3){
            for (i=0; i<3; i++){
                resources.add(tray[i][col-1]);
            }

            for (i=1; i<3; i++){
                tray[i][col-1] = tmptray[i-1][col-1];
            }

            tray[0][col-1] = slide;
            this.slide = resources.get(resources.size()-1);
        }   else throw new IndexOutOfBoundsException();


        //resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE)); aggiungere anche if has bonus swapwhite
        return resources;
    }


}
