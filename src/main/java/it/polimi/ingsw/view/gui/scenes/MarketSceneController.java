package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MarketSceneController extends ClientObservable implements GenericSceneController {
    @FXML
    private ImageView slideMrbl;
    @FXML
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

    }

    private void whenColumn2BtnClicked(MouseEvent event){

    }

    private void whenColumn3BtnClicked(MouseEvent event){

    }

    private void whenColumn4BtnClicked(MouseEvent event){

    }

    private void whenRow1BtnClicked(MouseEvent event){

    }

    private void whenRow2BtnClicked(MouseEvent event){

    }

    private void whenRow3BtnClicked(MouseEvent event){

    }

}
