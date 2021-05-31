package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.ResourcePlacement;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
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

public class DepoPopupv2Controller extends ClientObservable implements GenericSceneController,GenericPopupController {
    private Stage stage;
    private ModelView modelView;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private BorderPane rootPane;
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
    @FXML
    private ImageView firstLevelLeft;
    @FXML
    private ImageView firstLevelCenter;
    @FXML
    private ImageView firstLevelRight;
    @FXML
    private ImageView secondLevelLeft;
    @FXML
    private ImageView secondLevelRight;
    @FXML
    private ImageView thirdLevel;
    @FXML
    private Button firstLevelLeftBtn;
    @FXML
    private Button firstLevelCenterBtn;
    @FXML
    private Button firstLevelRightBtn;
    @FXML
    private Button secondLevelLeftBtn;
    @FXML
    private Button secondLevelRightBtn;
    @FXML
    private Button thirdLevelBtn;

    public DepoPopupv2Controller(ModelView modelView){
        stage = new Stage();
        this.modelView = modelView;
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
    }

    public void initialize(){
        rootPane.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);

        firstLevelLeftBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstLevelLeftBtnClicked);
        firstLevelCenterBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstLevelCenterBtnClicked);
        firstLevelRightBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstLevelRightBtnClicked);
        secondLevelLeftBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondLevelLeftBtnClicked);
        secondLevelRightBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondLevelRightBtnClicked);
        thirdLevelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenThirdLevelBtnClicked);

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

    private void whenFirstLevelLeftBtnClicked(MouseEvent event){

    }

    private void whenFirstLevelCenterBtnClicked(MouseEvent event){

    }

    private void whenFirstLevelRightBtnClicked(MouseEvent event){

    }

    private void whenSecondLevelLeftBtnClicked(MouseEvent event){

    }

    private void whenSecondLevelRightBtnClicked(MouseEvent event){

    }

    private void whenThirdLevelBtnClicked(MouseEvent event){

    }

    public void whenSwitchBtnClicked(MouseEvent event){
        disableAll();

    }

    public void whenExtraBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "extra"));
    }

    public void whenDiscardBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "discard"));
    }

    public void disableAll(){
        switchBtn.setDisable(true);
        extraBtn.setDisable(true);
        discardBtn.setDisable(true);
        firstLevelLeftBtn.setDisable(true);
        firstLevelCenterBtn.setDisable(true);
        firstLevelRightBtn.setDisable(true);
        secondLevelLeftBtn.setDisable(true);
        secondLevelRightBtn.setDisable(true);
        thirdLevelBtn.setDisable(true);
    }

    public void setResourceLbl(String resource){
        resourceLbl.setText(resource);
    }

    /*
    public void setResourceImg(String resource) {
        PersonalBoardSceneController.setImageDepositSpot(resource, resourceImg);
    }

    */


    public void showPopUp(){
        stage.showAndWait();
    }
    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
