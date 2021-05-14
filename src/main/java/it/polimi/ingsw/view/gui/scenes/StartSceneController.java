package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.Observable;
import it.polimi.ingsw.observerPattern.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class StartSceneController extends ViewObservable implements GenericSceneController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;

    @FXML
    public void initialize(){
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onStartButtonClick);
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> System.exit(0) );
    }

    private void onStartButtonClick(Event event){
        SceneController.changeRootPane(observers, event,  "ConnectionScene.fxml");
    }
}