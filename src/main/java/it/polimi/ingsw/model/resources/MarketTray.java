package it.polimi.ingsw.model.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Where the marbles are placed and players take resources
 */
public class MarketTray implements Serializable{
    private Resource[][] tray = new Resource[3][4];
    private Resource slide;

    public MarketTray() {
        int k=0;
        ArrayList<Resource> marbles = createMarbles();
        slide = marbles.get(12);
        marbles.remove(12);

        for (int i=0; i<3; i++) {
            for (int j = 0; j < 4; j++) {
                tray[i][j] = new Resource(marbles.get(k).getQnt(), marbles.get(k).getType());
                k++;
            }
        }

    }

    public Resource[][] getTray() {
        return tray;
    }


    public ArrayList<Resource> createMarbles(){
        ArrayList<Resource> marbles = new ArrayList<>();
        for (int i=0; i<4; i++){
            marbles.add(new Resource(1,ResourceType.WHITERESOURCE));
        }
        for (int i=0; i<2; i++){
            marbles.add(new Resource(1,ResourceType.STONE));
            marbles.add(new Resource(1, ResourceType.SHIELD));
            marbles.add(new Resource(1, ResourceType.SERVANT));
            marbles.add(new Resource(1, ResourceType.COIN));
        }
        marbles.add(new Resource(1, ResourceType.FAITHPOINT));
        Collections.shuffle(marbles);
        return marbles;
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

        if (col >= 1 && col <= 4){
            for (i=0; i<3; i++){
                resources.add(tray[i][col-1]);
            }

            for (i=1; i<3; i++){
                tray[i][col-1] = tmptray[i-1][col-1];
            }

            tray[0][col-1] = slide;
            this.slide = resources.get(resources.size()-1);
        }   else throw new IndexOutOfBoundsException();

        return resources;
    }






    @Override
    public String toString() {
        String text, text2;

        text = Arrays.stream(tray)
                .map(Arrays::toString )
                .collect(Collectors.joining(System.lineSeparator()));

        return ("MARKET TRAY: \n" +
                text +
                "\nSLIDER: \n" +
                slide + "\n");
    }
}
