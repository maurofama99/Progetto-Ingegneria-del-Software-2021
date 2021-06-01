package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.Gui;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class PlayerLoginSceneController extends ClientObservable implements GenericSceneController{

    private String nickname;
    private boolean solo = false;

    public void setSolo(boolean solo) {
        this.solo = solo;
        numberOfPlayersField.setDisable(true);
    }

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
        exitGameButton.setDisable(true);

        nickname = nicknameField.getText();
        nickname = nickname.replaceAll("\\s+","");

        if (!solo) {
            int numberOfPlayers = Integer.parseInt(numberOfPlayersField.getText()); //todo try catch che gestisce il parseInt
            notifyObservers(new LoginData(nickname, numberOfPlayers));
        } else notifyObservers(new LoginData(nickname, 1));

    }

    private void onExitGameClick(Event event){
        joinGameButton.setDisable(true);
        exitGameButton.setDisable(true);

        SceneController.changeRootPane(clientObservers, event, "connection_scene.fxml");
    }

    public String getNickname() {
        return nickname;
    }
}
