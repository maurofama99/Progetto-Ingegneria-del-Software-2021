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

    private final Gui gui;
    private String nickname;

    @FXML
    private TextField nicknameField;

    @FXML
    private Button joinGameButton;

    @FXML
    private TextField numberOfPlayersField;

    @FXML
    private Button exitGameButton;

    public PlayerLoginSceneController(Gui gui) {
        this.gui = gui;
    }

    @FXML
    public void initialize(){
        joinGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinGameClick);
        exitGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onExitGameClick);
    }

    private void onJoinGameClick(Event event){
        joinGameButton.setDisable(true);
        exitGameButton.setDisable(true);
        //vedere se non disabilitar il back nel caso

        SceneController.showPopup("Wait...", "Waiting for other players to join...");
        nickname = nicknameField.getText();
        nickname = nickname.replaceAll("\\s+","");
        gui.setNickname(nickname);
        int numberOfPlayers = Integer.parseInt(numberOfPlayersField.getText());

        notifyObservers(new LoginData(nickname, numberOfPlayers));

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
