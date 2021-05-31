package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.messagescs.GoingMarket;
import it.polimi.ingsw.observerPattern.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MarketSceneController extends ClientObservable implements GenericSceneController {
    @FXML
    private GridPane marketTray;
    @FXML
    private ImageView slideMrbl;
    /*@FXML
    private ImageView row1column1;
    @FXML
    private ImageView row1column2;
    @FXML
    private ImageView row1column3;
    @FXML
    private ImageView row1column4;
    @FXML
    private ImageView row2column1;
    @FXML
    private ImageView row2column2;
    @FXML
    private ImageView row2column3;
    @FXML
    private ImageView row2column4;
    @FXML
    private ImageView row3column1;
    @FXML
    private ImageView row3column2;
    @FXML
    private ImageView row3column3;
    @FXML
    private ImageView row3column4;
    */
    @FXML
    private Button column1Btn;
    @FXML
    private Button column2Btn;
    @FXML
    private Button column3Btn;
    @FXML
    private Button column4Btn;
    @FXML
    private Button row1Btn;
    @FXML
    private Button row2Btn;
    @FXML
    private Button row3Btn;

    private MarketTray marketTrayArray;


    public void initialize(){


        column1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenColumn1BtnClicked);
        column2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenColumn2BtnClicked);
        column3Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenColumn3BtnClicked);
        column4Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenColumn4BtnClicked);
        row1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRow1BtnClicked);
        row2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRow2BtnClicked);
        row3Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRow3BtnClicked);
    }

    private void whenColumn1BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(1, false));
        disableAll();
    }

    private void whenColumn2BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(2, false));
        disableAll();
    }

    private void whenColumn3BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(3, false));
        disableAll();
    }

    private void whenColumn4BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(4, false));
        disableAll();
    }

    private void whenRow1BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(1, true));
        disableAll();
    }

    private void whenRow2BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(2, true));
        disableAll();
    }

    private void whenRow3BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(3, true));
        disableAll();
    }

    private void disableAll() {
        column1Btn.setDisable(true);
        column3Btn.setDisable(true);
        column2Btn.setDisable(true);
        row1Btn.setDisable(true);
        row2Btn.setDisable(true);
        row3Btn.setDisable(true);
        column4Btn.setDisable(true);
    }

    public void setMarketTrayArray(MarketTray marketTrayArray) {
        this.marketTrayArray = marketTrayArray;
    }

    public ImageView setMarble(ResourceType resource){

        ImageView imageView = new ImageView();
        Image cyan = new Image("/punchboard/marbles/cyan.png");
        Image grey = new Image("/punchboard/marbles/grey.png");
        Image purple = new Image("/punchboard/marbles/purple.png");
        Image red = new Image("/punchboard/marbles/red.png");
        Image white = new Image("/punchboard/marbles/white.png");
        Image yellow = new Image("/punchboard/marbles/yellow.png");

        switch(resource){
            case SHIELD :
                imageView.setImage(cyan);
                break;
            case SERVANT :
                imageView.setImage(purple);
                break;
            case STONE :
                imageView.setImage(grey);
                break;
            case COIN:
                imageView.setImage(yellow);
                break;
            case WHITERESOURCE :
                imageView.setImage(white);
                break;
            case FAITHPOINT:
                imageView.setImage(red);
                break;
        }

        return imageView;
    }

    public void setMarbles(MarketTray marketTrayArray){

        slideMrbl = setMarble(marketTrayArray.getSlide().getType());

        for(int j=0; j<3;j++){
            for(int k=0; k<4; k++){
                marketTray.add(setMarble(marketTrayArray.getTray()[j][k].getType()), j,k);
            }
        }

    }

}
