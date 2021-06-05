package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.messagescs.ResourceTypeChosen;
import it.polimi.ingsw.network.messagescs.SwappedResource;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Button firstBtn;
    @FXML
    private Button secondBtn;
    @FXML
    private ImageView firstResource;
    @FXML
    private ImageView secondResource;


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

        firstBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenFirstBtnPressed);
        secondBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenSecondBtnPressed);

        setResourceImages();
    }

    private void setResourceImages() {
        firstResource.setImage(new Image("/punchboard/resources/"+ty1.getResourceName().toUpperCase()+".png"));
        secondResource.setImage(new Image("/punchboard/resources/"+ty2.getResourceName().toUpperCase()+".png"));
        firstBtn.setText(ty1.getResourceName());
        secondBtn.setText(ty2.getResourceName());
    }

    private void whenSecondBtnPressed(MouseEvent event) {
        notifyObservers(new SwappedResource("client", ty2.getResourceName()));
        stage.close();
    }

    private void whenFirstBtnPressed(MouseEvent event) {
        notifyObservers(new SwappedResource("client", ty1.getResourceName()));
        stage.close();
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
