package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ServerHandler;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.Gui;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.Socket;


public class ConnectionSceneController extends ClientObservable implements GenericSceneController {

    private Client client;
    private Socket server;
    private boolean connected = false;

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
        client = (Client) clientObservers.get(0);

        while (!connected){
            try {
                int port = Integer.parseInt(serverPort.getText());
                server = new Socket(ip, port);
                client.run();
                client.startGuiGame(ip, server);
                connected = true;
            } catch (IllegalArgumentException | IOException e) {
                SceneController.changeRootPane(clientObservers,"connection_scene.fxml");
                PopupSceneController psc = new PopupSceneController("Server unreachable,\ntry with another address");
                psc.setTitleLabel("Error");
                psc.addAllClientObservers(clientObservers);
                Platform.runLater(() -> SceneController.showPopup(psc, "popup_scene.fxml"));
                break;
            }
        }

    }

    private void exitButtonClick(Event event){
        connectButton.setDisable(true);
        exitButton.setDisable(true);

        SceneController.changeRootPane(clientObservers, event, "start_scene.fxml");
    }
}
