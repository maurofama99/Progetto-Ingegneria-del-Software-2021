package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.observerPattern.Observable;
import it.polimi.ingsw.observerPattern.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class PlayerLoginSceneController extends ClientObservable implements GenericSceneController{
    @FXML
    private TextField nicknameField;

    @FXML
    private Button joinGameButton;

    @FXML
    private TextField numberOfPlayersField;

    @FXML
    private Button exitGameButton;

    @FXML
    public void initialize(){
        joinGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinGameClick);
        exitGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onExitGameClick);
    }

    private void onJoinGameClick(Event event){
        joinGameButton.setDisable(true);

        String nickname = nicknameField.getText();
        nickname = nickname.replaceAll("\\s+","");
        int numberOfPlayers = Integer.parseInt(numberOfPlayersField.getText());



    }

    private void onExitGameClick(Event event){
        joinGameButton.setDisable(true);
        exitGameButton.setDisable(true);


    }
}
