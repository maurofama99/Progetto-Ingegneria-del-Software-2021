package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SingleplayerPopupSceneController extends ClientObservable implements GenericPopupController {
    private final Stage stage;

    private Token token;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Label titleLabel = new Label();
    @FXML
    private Label messageOfLabel = new Label();
    /*@FXML
    private Button ConfirmButton;*/
    @FXML
    private ImageView tokenPlayed;

    @FXML
    public void initialize(){
        setTokenImage();
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        //ConfirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenConfirmButtonClicked);
    }

    public SingleplayerPopupSceneController(Token token){
        this.token = token;
        messageOfLabel.setText("Lorenzo plays : ");
        stage = new Stage();
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.NONE);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 150;
        y_Offset = 150;
    }



    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getSceneX();
        y_Offset = stage.getY() - event.getSceneY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() - x_Offset);
        stage.setY(event.getScreenY() - y_Offset);
    }


    public void setTokenImage(){
        tokenPlayed.setImage(new Image("/punchboard/token-"+token.getTokenAction().toStringGui()+".png"));
    }

    private void whenConfirmButtonClicked(MouseEvent event){
        stage.close();
    }

    public void showPopUp(){
        stage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
