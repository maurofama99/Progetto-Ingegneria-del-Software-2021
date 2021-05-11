package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.Observable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class PlayerLoginSceneController extends Observable implements GenericSceneController{
    @FXML
    private TextField nicknameField;

    @FXML
    private Button joinGameButton;

    @FXML
    private Button exitGameButton;

    @FXML
    public void init(){
        joinGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinGameClick);
        exitGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onExitGameClick);
    }

    private void onJoinGameClick(Event event){
        joinGameButton.setDisable(true);

        String nickname = nicknameField.getText();
    }

    private void onExitGameClick(Event event){
        joinGameButton.setDisable(true);
        exitGameButton.setDisable(true);


    }
}
