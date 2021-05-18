package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class PlayerResourceChoosingController implements GenericSceneController {

    @FXML
    private StackPane rootPane;
    @FXML
    private Button coinBtn;
    @FXML
    private Button stoneBtn;
    @FXML
    private Button servantBtn;
    @FXML
    private Button shieldBtn;

    @FXML
    public void initialize(){
        coinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenCoinClicked);
        stoneBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenStoneClicked);
        servantBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenServantClicked);
        shieldBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShieldClicked);
    }

    private void whenCoinClicked(MouseEvent event){

    }

    private void whenStoneClicked(MouseEvent event){

    }

    private void whenServantClicked(MouseEvent event){

    }

    private void whenShieldClicked(MouseEvent event){

    }
}
