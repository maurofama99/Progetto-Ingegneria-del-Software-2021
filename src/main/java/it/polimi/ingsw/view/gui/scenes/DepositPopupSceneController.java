package it.polimi.ingsw.view.gui.scenes;

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

public class DepositPopupSceneController implements GenericSceneController{

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
    private Button level1Button;
    @FXML
    private Button level2Button;
    @FXML
    private Button level3Button;
    @FXML
    private Label resourceLbl;
    @FXML
    private ImageView resourceImg;

    public DepositPopupSceneController(){
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
        level1Button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLvl1ButtonPressed );
        level2Button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLvl2ButtonPressed );
        level3Button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLvl3ButtonPressed );
    }

    private void whenLvl1ButtonPressed(MouseEvent event){

    }

    private void whenLvl2ButtonPressed(MouseEvent event){

    }

    private void whenLvl3ButtonPressed(MouseEvent event){

    }

    public void setResourceLbl(String resource){

    }

    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }


}
