package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.observerPattern.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class ConnectionSceneController extends ClientObservable implements GenericSceneController {

    @FXML
    private Parent Rootpane;
    @FXML
    private TextField serverAddress;
    @FXML
    private TextField serverPort;
    @FXML
    private Button connectButton;
    @FXML
    private Button exitButton;

    @FXML
    public void initialize(){
        connectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::connectButtonClick);
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::exitButtonClick);
    }

    private void connectButtonClick(Event event){
        String ip = serverAddress.getText();
        int port = Integer.parseInt(serverPort.getText());

        Gui view = new Gui();
        Client client = new Client(view, port, ip);
        view.addClientObserver(client);
        client.run();

        exitButton.setDisable(true);
        connectButton.setDisable(true);

        //funziona anche senza platìorm run alter credo
        SceneController.changeRootPane(clientObservers, "player_login_scene.fxml");

    }

    private void exitButtonClick(Event event){

        SceneController.changeRootPane(clientObservers, event, "start_scene.fxml");
    }
}
