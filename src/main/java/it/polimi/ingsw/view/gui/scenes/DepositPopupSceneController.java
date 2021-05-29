package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.ResourcePlacement;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class DepositPopupSceneController  extends ClientObservable implements GenericSceneController{

    private final Stage stage;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private BorderPane rootPane;
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
    @FXML
    private Button switchBtn;
    @FXML
    private Button extraBtn;
    @FXML
    private Button discardBtn;

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
        rootPane.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        level1Button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLvl1ButtonClicked );
        level2Button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLvl2ButtonClicked );
        level3Button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLvl3ButtonClicked );
        switchBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSwitchBtnClicked);
        extraBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenExtraBtnClicked);
        discardBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDiscardBtnClicked);
    }

    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    private void whenLvl1ButtonClicked(MouseEvent event){
        level1Button.setDisable(true);
        level2Button.setDisable(true);
        level3Button.setDisable(true);
        switchBtn.setDisable(true);
        extraBtn.setDisable(true);
        discardBtn.setDisable(true);
        notifyObservers(new ResourcePlacement("nickname", "1"));


        stage.close();
    }

    private void whenLvl2ButtonClicked(MouseEvent event){
        level1Button.setDisable(true);
        level2Button.setDisable(true);
        level3Button.setDisable(true);
        switchBtn.setDisable(true);
        extraBtn.setDisable(true);
        discardBtn.setDisable(true);
        notifyObservers(new ResourcePlacement("nickname", "2"));

        stage.close();
    }

    private void whenLvl3ButtonClicked(MouseEvent event){
        level1Button.setDisable(true);
        level2Button.setDisable(true);
        level3Button.setDisable(true);
        switchBtn.setDisable(true);
        extraBtn.setDisable(true);
        discardBtn.setDisable(true);
        notifyObservers(new ResourcePlacement("nickname", "3"));

        stage.close();
    }

    public void whenSwitchBtnClicked(MouseEvent event){
        level1Button.setDisable(true);
        level2Button.setDisable(true);
        level3Button.setDisable(true);
        switchBtn.setDisable(true);
        extraBtn.setDisable(true);
        discardBtn.setDisable(true);

    }

    public void whenExtraBtnClicked(MouseEvent event){
        level1Button.setDisable(true);
        level2Button.setDisable(true);
        level3Button.setDisable(true);
        switchBtn.setDisable(true);
        extraBtn.setDisable(true);
        discardBtn.setDisable(true);
        notifyObservers(new ResourcePlacement("nickname", "extra"));
    }

    public void whenDiscardBtnClicked(MouseEvent event){
        level1Button.setDisable(true);
        level2Button.setDisable(true);
        level3Button.setDisable(true);
        switchBtn.setDisable(true);
        extraBtn.setDisable(true);
        discardBtn.setDisable(true);
        notifyObservers(new ResourcePlacement("nickname", "discard"));
    }

    public void setResourceLbl(String resource){
        resourceLbl.setText(resource);
    }

    public void setResourceImg(String resource) {
        PersonalBoardSceneController.setImageDepositSpot(resource, resourceImg);
    }
    public void showPopUp(){
        stage.showAndWait();
    }


    public void setScene(Scene scene){
        stage.setScene(scene);
    }


}
