package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.Gui;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class PlayerLoginSceneController extends ClientObservable implements GenericSceneController{

    private String nickname;
    private boolean solo = false;
    private int numPlayers;

    public void setSolo(boolean solo) {
        this.solo = solo;
        disableAll();
    }

    @FXML
    private TextField nicknameField;

    @FXML
    private Button joinGameButton;

    @FXML
    private Button exitGameButton;

    @FXML
    private Button singleBtn, twoPlayers, threePlayers, fourPlayers;

    @FXML
    public void initialize(){
        joinGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinGameClick);
        exitGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onExitGameClick);

        singleBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSingleClicked);
        twoPlayers.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenTwoClicked);
        threePlayers.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenThreeClicked);
        fourPlayers.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFourPlayers);
    }

    private void whenSingleClicked(MouseEvent event){
        numPlayers = 1;
        disableAll();
    }

    private void whenTwoClicked(MouseEvent event){
        numPlayers = 2;
        disableAll();
    }

    private void whenThreeClicked(MouseEvent event){
        numPlayers = 3;
        disableAll();
    }

    private void whenFourPlayers(MouseEvent event){
        numPlayers = 4;
        disableAll();
    }

    private void onJoinGameClick(Event event){
        joinGameButton.setDisable(true);
        exitGameButton.setDisable(true);

        nickname = nicknameField.getText();
        nickname = nickname.replaceAll("\\s+","");

        if(nickname.matches("")){
            SceneController.changeRootPane(clientObservers, "player_login_scene.fxml");
            PopupSceneController psc= new PopupSceneController("Please enter your nickname");
            psc.addAllClientObservers(clientObservers);
            SceneController.showPopup(psc, "popup_scene.fxml");
        } else {
            if (!solo) notifyObservers(new LoginData(nickname, numPlayers));
            else notifyObservers(new LoginData(nickname, 1));
        }

    }

    private void onExitGameClick(Event event){
        System.exit(0);
    }

    public String getNickname() {
        return nickname;
    }

    public void disableAll(){
        singleBtn.setDisable(true);
        twoPlayers.setDisable(true);
        threePlayers.setDisable(true);
        fourPlayers.setDisable(true);
    }
}
