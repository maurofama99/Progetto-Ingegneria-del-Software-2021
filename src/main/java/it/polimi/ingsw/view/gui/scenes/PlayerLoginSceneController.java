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

    }

    private void whenTwoClicked(MouseEvent event){

    }

    private void whenThreeClicked(MouseEvent event){

    }

    private void whenFourPlayers(MouseEvent event){

    }

    private void onJoinGameClick(Event event){
        joinGameButton.setDisable(true);
        exitGameButton.setDisable(true);

        nickname = nicknameField.getText();
        nickname = nickname.replaceAll("\\s+","");

        if (!solo) {
            try {
                int numberOfPlayers = Integer.parseInt(numberOfPlayersField.getText());
                notifyObservers(new LoginData(nickname, numberOfPlayers));
            } catch (NumberFormatException e){
                Platform.runLater(() -> SceneController.changeRootPane(clientObservers, "player_login_scene.fxml"));
                PopupSceneController psc = new PopupSceneController("That's not a number!");
                psc.setTitleLabel("Error");
                psc.addAllClientObservers(clientObservers);
                Platform.runLater(() -> SceneController.showPopup(psc, "popup_scene.fxml"));
            }
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
