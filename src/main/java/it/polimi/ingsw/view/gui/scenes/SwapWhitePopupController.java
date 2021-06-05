package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.messagescs.ResourceTypeChosen;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SwapWhitePopupController extends ClientObservable implements GenericPopupController {

    private final Stage stage;

    private final ResourceType ty1, ty2;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private StackPane rootPane;
    @FXML
    private Button coinBtn;
    @FXML
    private Button stoneBtn;
    @FXML
    private Button servantBtn;
    @FXML
    private Button shieldBtn;


    public SwapWhitePopupController(ResourceType ty1, ResourceType ty2){
        stage = new Stage();
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
        this.ty1 = ty1;
        this.ty2 = ty2;
    }

    @FXML
    public void initialize(){
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);

        coinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenCoinClicked);
        stoneBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenStoneClicked);
        servantBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenServantClicked);
        shieldBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShieldClicked);
    }

    /**
     * These two methods manages the tracking process of the window.
     * @param event the mouse event selected in the initialize method
     */
    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    private void whenShieldClicked(MouseEvent event){

    }

    private void whenServantClicked(MouseEvent event){

    }

    private void whenCoinClicked(MouseEvent event){

    }

    private void whenStoneClicked(MouseEvent event){

    }

    /**
     * These two methods are called to manage the popup scene
     */
    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
