package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.ResourcePlacement;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DepoPopupController extends ClientObservable implements GenericPopupController {
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
    private Button thirdLevelBtn = new Button();
    @FXML
    private Button secondLevelLeftBtn = new Button();
    @FXML
    private Button secondLevelRightBtn = new Button();

    @FXML
    private Button firstLevelLeftBtn = new Button();
    @FXML
    private Button firstLevelCenterBtn = new Button();
    @FXML
    private Button firstLevelRightBtn = new Button();

    @FXML
    private Button switchBtn = new Button();
    @FXML
    private Button discardBtn = new Button();
    @FXML
    private Button extraBtn = new Button();

    @FXML
    private ImageView thirdLevel = new ImageView();

    @FXML
    private ImageView secondLevelLeft  = new ImageView();
    @FXML
    private ImageView secondLevelRight = new ImageView();

    @FXML
    private ImageView firstLevelLeft = new ImageView();
    @FXML
    private ImageView firstLevelCenter = new ImageView();
    @FXML
    private ImageView firstLevelRight = new ImageView();




    public DepoPopupController(ModelView modelView){
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
        firstLevelLeftBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstLevelBtnClicked);
        firstLevelCenterBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstLevelBtnClicked);
        firstLevelRightBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstLevelBtnClicked);
        secondLevelLeftBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondLevelBtnClicked);
        secondLevelRightBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondLevelBtnClicked);
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

    private void whenFirstLevelBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "3"));
        stage.close();
    }


    private void whenSecondLevelBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "2"));
        stage.close();
    }


    private void whenThirdLevelBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "1"));
        stage.close();
    }

    public void whenSwitchBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "switch"));
        stage.close();
    }

    public void whenExtraBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "extra"));
        stage.close();
    }

    public void whenDiscardBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "discard"));
        stage.close();
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
