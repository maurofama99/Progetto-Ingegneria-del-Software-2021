package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.Gui;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class PlayerLoginSceneController extends ClientObservable implements GenericSceneController{

    ObservableList<String> players = FXCollections.observableArrayList("single", "2", "3", "4");
    private String nickname;
    private boolean solo = false;
    private int numPlayers;

    public void setSolo(boolean solo) {
        this.solo = solo;
        choiceBox.setDisable(true);
    }

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField nicknameField;

    @FXML
    private Button joinGameButton;

    @FXML
    private Button exitGameButton;


    @FXML
    public void initialize(){
        choiceBox.setItems(players);
        joinGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinGameClick);
        exitGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onExitGameClick);
    }


    private void onJoinGameClick(Event event){
        joinGameButton.setDisable(true);
        exitGameButton.setDisable(true);

        nickname = nicknameField.getText();
        nickname = nickname.replaceAll("\\s+","");

        if(nickname.matches("")){
            if (solo) Platform.runLater(() ->((PlayerLoginSceneController)SceneController.changeRootPane(clientObservers, event, "player_login_scene.fxml")).setSolo(true));
            else SceneController.changeRootPane(clientObservers, "player_login_scene.fxml");
            PopupSceneController psc= new PopupSceneController("Please enter your nickname");
            psc.addAllClientObservers(clientObservers);
            SceneController.showPopup(psc, "popup_scene.fxml");
        } else {
            if (!solo) {
                try {
                    switch (choiceBox.getSelectionModel().getSelectedItem()) {
                        case "single":
                            numPlayers = 1;
                            break;
                        case "2":
                            numPlayers = 2;
                            break;
                        case "3":
                            numPlayers = 3;
                            break;
                        case "4":
                            numPlayers = 4;
                            break;
                    }
                    notifyObservers(new LoginData(nickname, numPlayers));
                } catch (NullPointerException e){
                    SceneController.changeRootPane(clientObservers, "player_login_scene.fxml");
                    PopupSceneController psc= new PopupSceneController("Please choose the number of players");
                    psc.addAllClientObservers(clientObservers);
                    SceneController.showPopup(psc, "popup_scene.fxml");
                }
            }
            else notifyObservers(new LoginData(nickname, 1));
        }

    }

    private void onExitGameClick(Event event){
        System.exit(0);
    }

    public String getNickname() {
        return nickname;
    }

}
