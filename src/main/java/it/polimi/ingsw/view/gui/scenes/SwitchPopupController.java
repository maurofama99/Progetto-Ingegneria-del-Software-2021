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

public class SwitchPopupController extends ClientObservable implements GenericSceneController {


    private final Stage stage;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private Button srcFloor1Btn;
    @FXML
    private Button srcFloor2Btn;
    @FXML
    private Button srcFloor3Btn;
    @FXML
    private Button dstnFloor1Btn;
    @FXML
    private Button dstnFloor2Btn;
    @FXML
    private Button dstnFloor3Btn;
    @FXML
    private BorderPane rootPane;

    public SwitchPopupController(){
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
        srcFloor1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSrcFloor1BtnClicked);
        srcFloor2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSrcFloor2BtnClicked);
        srcFloor3Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSrcFloor3BtnClicked);
        dstnFloor1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDstnFloor1BtnClicked);
        dstnFloor2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDstnFloor2BtnClicked);
        dstnFloor3Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDstnFloor3BtnClicked);

    }


    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    private void whenSrcFloor1BtnClicked(MouseEvent event){

    }

    private void whenSrcFloor2BtnClicked(MouseEvent event){

    }

    private void whenSrcFloor3BtnClicked(MouseEvent event){

    }

    private void whenDstnFloor1BtnClicked(MouseEvent event){

    }

    private void whenDstnFloor2BtnClicked(MouseEvent event){

    }

    private void whenDstnFloor3BtnClicked(MouseEvent event){

    }

    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
