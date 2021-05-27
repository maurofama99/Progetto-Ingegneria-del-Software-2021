package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LeaderStartSceneController extends ClientObservable implements GenericSceneController {

    private final Stage stage;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Button activateLeaderBtn;
    @FXML
    private Button discardLeaderBtn;
    @FXML
    private Button closePopupBtn;

    public LeaderStartSceneController(){
        stage = new Stage();
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
    }

    @FXML
    public void initialize(){
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        activateLeaderBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenActivateLeaderButtonClicked);
        discardLeaderBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDiscardLeaderBtnClicked);
        closePopupBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenClosePopupBtnClicked);
    }

    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    private void whenActivateLeaderButtonClicked(MouseEvent event){

        stage.close();
    }

    private void whenDiscardLeaderBtnClicked(MouseEvent event){

        stage.close();
    }

    private void whenClosePopupBtnClicked(MouseEvent event){

        stage.close();
    }

    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}

