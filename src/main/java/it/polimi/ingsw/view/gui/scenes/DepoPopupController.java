package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.messagescs.ResourcePlacement;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DepoPopupController extends ClientObservable implements GenericPopupController {

    private Stage stage;
    private ModelView modelView;
    private Resource resource;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Label resourceLbl;
    @FXML
    private ImageView resourceImg;

    @FXML
    private Button thirdLevelBtn, secondLevelLeftBtn, secondLevelRightBtn;

    @FXML
    private Button firstLevelLeftBtn, firstLevelCenterBtn, firstLevelRightBtn;

    @FXML
    private Button switchBtn, discardBtn,  extraBtn;

    @FXML
    private ImageView thirdLevel, secondLevelLeft, secondLevelRight;

    @FXML
    private ImageView firstLevelLeft, firstLevelCenter, firstLevelRight;




    public DepoPopupController(Resource resource, ModelView modelView){
        stage = new Stage();
        this.modelView = modelView;
        this.resource = resource;
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.NONE);
        //stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
    }

    public void initialize(){

        setResourceImage(resource);
        setDepotImage();
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
        stage.close();
        Platform.runLater(()-> {
            SwitchPopupController spc = new SwitchPopupController();
            spc.addAllClientObservers(clientObservers);
            SceneController.showPopup(spc, "switch_popup.fxml");
        });

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

    public void setResourceImage(Resource resource) {
        resourceImg.setImage(new Image("/punchboard/resources/"+resource.toStringGui()+".png"));
    }

    public void setFloorImg(Resource resource, ImageView spot) {
        spot.setImage(new Image("/punchboard/resources/"+resource.toStringGui()+".png"));
    }

    private void setDepotImage(){
        if (!modelView.getWarehouse().getFloors().get(0).getType().equals(ResourceType.NULLRESOURCE)){
            setFloorImg(modelView.getWarehouse().getFloors().get(0), thirdLevel);
        }
        if (!modelView.getWarehouse().getFloors().get(1).getType().equals(ResourceType.NULLRESOURCE)){
            if (modelView.getWarehouse().getFloors().get(1).getQnt()==1)
                setFloorImg(modelView.getWarehouse().getFloors().get(1), secondLevelLeft);
            else if ((modelView.getWarehouse().getFloors().get(1).getQnt()==2))
                setFloorImg(modelView.getWarehouse().getFloors().get(1), secondLevelLeft);
            setFloorImg(modelView.getWarehouse().getFloors().get(1), secondLevelRight);
        }
        if (!modelView.getWarehouse().getFloors().get(2).getType().equals(ResourceType.NULLRESOURCE)){
            if (modelView.getWarehouse().getFloors().get(2).getQnt()==1)
                setFloorImg(modelView.getWarehouse().getFloors().get(2), firstLevelLeft);
            else if (modelView.getWarehouse().getFloors().get(2).getQnt()==2){
                setFloorImg(modelView.getWarehouse().getFloors().get(2), firstLevelRight);
                setFloorImg(modelView.getWarehouse().getFloors().get(2), firstLevelLeft);
            }
            else {
                setFloorImg(modelView.getWarehouse().getFloors().get(2), firstLevelRight);
                setFloorImg(modelView.getWarehouse().getFloors().get(2), firstLevelLeft);
                setFloorImg(modelView.getWarehouse().getFloors().get(2), firstLevelCenter);
            }
        }
    }

    public void showPopUp(){
        stage.show();
    }
    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
