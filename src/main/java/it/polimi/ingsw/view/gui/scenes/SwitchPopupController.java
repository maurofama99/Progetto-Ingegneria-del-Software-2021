package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.ResourcePlacement;
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

/**
 * Popup for managing the resources in the depot.
 */
public class SwitchPopupController extends ClientObservable implements GenericPopupController {


    private final Stage stage;
    private int src;

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

    /**
     * Simple constructor
     */
    public SwitchPopupController(){
        stage = new Stage();
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.NONE);
        //stage.setAlwaysOnTop(true);
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
        dstnFloor1Btn.setDisable(true);
        dstnFloor2Btn.setDisable(true);
        dstnFloor3Btn.setDisable(true);

    }

    /**
     * These two methods manages the tracking process of the window.
     * @param event the mouse event selected in the initialize method
     */
    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getSceneX();
        y_Offset = stage.getY() - event.getSceneY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() - x_Offset);
        stage.setY(event.getScreenY() - y_Offset);
    }


    /**
     * Three methods for asking the player where the initial resource to swap is
     * @param event
     */
    private void whenSrcFloor1BtnClicked(MouseEvent event){
        src = 1;
        disableSource();
        enableDest();
    }

    private void whenSrcFloor2BtnClicked(MouseEvent event){
        src = 2;
        disableSource();
        enableDest();
    }

    private void whenSrcFloor3BtnClicked(MouseEvent event){
        src = 3;
        disableSource();
        enableDest();
    }

    /**
     * Three methods for asking the player where he wants to put the new resource
     * @param event
     */
    private void whenDstnFloor1BtnClicked(MouseEvent event){
        notifyObservers(new ResourcePlacement("client", "switch", src, 1));
        stage.close();
    }

    private void whenDstnFloor2BtnClicked(MouseEvent event){
        notifyObservers(new ResourcePlacement("client", "switch", src, 2));
        stage.close();
    }

    private void whenDstnFloor3BtnClicked(MouseEvent event){
        notifyObservers(new ResourcePlacement("client", "switch", src, 3));
        stage.close();
    }

    /**
     * Two methods for switching from active to not the buttons.
     */
    public void disableSource(){
        srcFloor1Btn.setDisable(true);
        srcFloor2Btn.setDisable(true);
        srcFloor3Btn.setDisable(true);
    }

    public void enableDest(){
        dstnFloor1Btn.setDisable(false);
        dstnFloor2Btn.setDisable(false);
        dstnFloor3Btn.setDisable(false);
    }

    /**
     * These two methods are called to manage the popup scene
     */
    public void showPopUp(){
        stage.show();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
