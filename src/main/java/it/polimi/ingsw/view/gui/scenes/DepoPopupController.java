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

/**
 * The controller of the depot popup, which is the popup for when the player receives a resource that has to be placed
 * in the depot.
 */
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
     * Manages the click at the first level of the depot. Sends the resource there, if it is free or
     * if it has the same type of resource placed there.
     * @param event the event chosen in the initialize() method
     */
    private void whenFirstLevelBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "3"));
        stage.close();
    }

    /**
     * Manages the click at the second level of the depot. Sends the resource there, if it is free
     * or if it has the same type of resource placed
     * @param event the event chosen in the initialize() method
     */
    private void whenSecondLevelBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "2"));
        stage.close();
    }

    /**
     * Manages the click at the third level of the depot. Sends the resource there, if it is free
     * @param event the event chosen in the initialize() method
     */
    private void whenThirdLevelBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "1"));
        stage.close();
    }
    /**
     * Opens the switch popup to ask the player if he wants to switch the resource from a floor to another
     * @param event the event chosen in the initialize() method
     */
    public void whenSwitchBtnClicked(MouseEvent event){
        disableAll();
        stage.close();
        Platform.runLater(()-> {
            SwitchPopupController spc = new SwitchPopupController();
            spc.addAllClientObservers(clientObservers);
            SceneController.showPopup(spc, "switch_popup.fxml");
        });

    }
    /**
     * Opens the extra depot provided by the leader cards, if possible
     * @param event the event chosen in the initialize() method
     */
    public void whenExtraBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "extra"));
        stage.close();
    }
    /**
     * Discard the resource taken by the player. It makes other players' cross move forward
     * @param event the event chosen in the initialize() method
     */
    public void whenDiscardBtnClicked(MouseEvent event){
        disableAll();
        notifyObservers(new ResourcePlacement("nickname", "discard"));
        stage.close();
    }

    /**
     * Simple disable for all the buttons after a click
     */
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

    /**
     * Matches the resource name to its image
     * @param resource the resource present at that time, in that spot.
     */
    public void setResourceImage(Resource resource) {
        resourceImg.setImage(new Image("/punchboard/resources/"+resource.toStringGui()+".png"));
    }

    /**
     * Sets the image in the spot of the depot
     * @param resource resource present
     * @param spot the depot spot to set
     */
    public void setFloorImg(Resource resource, ImageView spot) {
        spot.setImage(new Image("/punchboard/resources/"+resource.toStringGui()+".png"));
    }

    /**
     * Initialize and updates everytime the images of the depot popup, so the player can actually see the
     * resource spawn in the depot. It uses the ModelView to update it.
     */
    private void setDepotImage(){
        if (!modelView.getWarehouse().getFloors().get(0).getType().equals(ResourceType.NULLRESOURCE)){
            setFloorImg(modelView.getWarehouse().getFloors().get(0), thirdLevel);
        }
        if (!modelView.getWarehouse().getFloors().get(1).getType().equals(ResourceType.NULLRESOURCE)){
            if (modelView.getWarehouse().getFloors().get(1).getQnt()==1)
                setFloorImg(modelView.getWarehouse().getFloors().get(1), secondLevelLeft);
            else if ((modelView.getWarehouse().getFloors().get(1).getQnt()==2)) {
                setFloorImg(modelView.getWarehouse().getFloors().get(1), secondLevelLeft);
                setFloorImg(modelView.getWarehouse().getFloors().get(1), secondLevelRight);
            }
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
