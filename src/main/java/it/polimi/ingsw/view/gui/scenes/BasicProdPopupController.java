package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BasicProdPopupController extends ClientObservable implements GenericPopupController {

    private final Stage stage;
    private int arrow;
    private String text;

    private double x_Offset;
    private double y_Offset;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Label message;
    @FXML
    private ImageView basicProd;
    @FXML
    private ImageView firstArrow;
    @FXML
    private ImageView secondArrow;
    @FXML
    private ImageView thirdArrow;
    @FXML
    private Button closeBtn;

    public BasicProdPopupController(int arrow, String message){
        stage = new Stage();
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.NONE);
        //stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
        this.arrow = arrow;
        this.text = message;
    }

    @FXML
    public void initialize(){
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::close);

        if (arrow == 1){
            firstArrow.setVisible(true);
            secondArrow.setVisible(false);
            thirdArrow.setVisible(false);
        } else if (arrow == 2){
            firstArrow.setVisible(false);
            secondArrow.setVisible(true);
            thirdArrow.setVisible(false);
        } else {
            firstArrow.setVisible(false);
            secondArrow.setVisible(false);
            thirdArrow.setVisible(true);
        }
        message.setText(text);
        this.message.setMinWidth(Region.USE_PREF_SIZE);
    }

    private void close(MouseEvent event) {
        stage.close();
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
     * These two methods are called to manage the popup scene
     */
    public void showPopUp(){
        stage.show();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
