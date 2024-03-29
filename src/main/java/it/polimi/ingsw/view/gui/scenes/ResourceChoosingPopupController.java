package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.ResourceTypeChosen;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ResourceChoosingPopupController extends ClientObservable implements GenericPopupController {
    private final Stage stage;

    private double x_Offset = 0;
    private double y_Offset = 0;

    private int resourceType;

    public int getResourceType() {
        return resourceType;
    }

    @FXML
    private BorderPane rootPane;
    @FXML
    private ImageView coin, stone, servant, shield;

    public ResourceChoosingPopupController(){
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

        coin.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenCoinClicked);
        stone.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenStoneClicked);
        servant.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenServantClicked);
        shield.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShieldClicked);
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
        stage.setX(event.getScreenX() + x_Offset);
        stage.setY(event.getScreenY() + y_Offset);
    }


    private void whenCoinClicked(MouseEvent event){
        resourceType = 2;
        notifyObservers(new ResourceTypeChosen("client", resourceType));

        coin.setDisable(true);
        stone.setDisable(true);
        servant.setDisable(true);
        shield.setDisable(true);
        stage.close();
    }

    private void whenStoneClicked(MouseEvent event){
        resourceType = 3;
        notifyObservers(new ResourceTypeChosen("client", resourceType));

        coin.setDisable(true);
        stone.setDisable(true);
        servant.setDisable(true);
        shield.setDisable(true);
        stage.close();
    }

    private void whenServantClicked(MouseEvent event){
        resourceType = 1;
        notifyObservers(new ResourceTypeChosen("client", resourceType));

        coin.setDisable(true);
        stone.setDisable(true);
        servant.setDisable(true);
        shield.setDisable(true);
        stage.close();
    }

    private void whenShieldClicked(MouseEvent event){
        resourceType = 0;
        notifyObservers(new ResourceTypeChosen("client", resourceType));

        coin.setDisable(true);
        stone.setDisable(true);
        servant.setDisable(true);
        shield.setDisable(true);
        stage.close();
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
