package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * The very first scene of the gui.
 */
public class StartSceneController extends ClientObservable implements GenericSceneController {

    private boolean solo = false;
    private Client client;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button startButton;
    //@FXML
    //private Button exitButton;

    @FXML
    public void initialize(){
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onStartButtonClick);
        //exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0) );
    }

    /**
     * Switch the scene to the connection one
     * @param event the event chosen in the initialize method
     */
    private void onStartButtonClick(Event event){

        startButton.setDisable(true);
        //exitButton.setDisable(true);

        if (solo){
            client.setEvent(event);
            client.run();
        } else SceneController.changeRootPane(clientObservers, event,  "connection_scene.fxml");

    }

    public void setSolo(boolean solo) {
        this.solo = solo;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
