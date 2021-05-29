package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SingleplayerPopupSceneController implements GenericSceneController {
    private final Stage stage;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Label titleLabel = new Label();
    @FXML
    private Label messageOfLabel = new Label();
    @FXML
    private Button ConfirmButton;
    @FXML
    private ImageView tokenPlayed;

    public SingleplayerPopupSceneController(){
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
        ConfirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenConfirmButtonClicked);
    }

    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    public void setTokenImage(Token token){
        //TODO: set token image
    }

    private void whenConfirmButtonClicked(MouseEvent event){
        stage.close();
    }

    public void setMessageOfLabel(String string) {
        messageOfLabel.setText("Lorenzo plays : " + string);
    }

    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
