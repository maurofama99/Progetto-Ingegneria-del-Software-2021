package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ProductionPopupSceneController extends ClientObservable implements GenericPopupController {
    private final Stage stage;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private BorderPane rootPane;
    @FXML
    private ImageView leftSlot;
    @FXML
    private ImageView centerSlot;
    @FXML
    private ImageView rightSlot;
    @FXML
    private Button basicBtn;
    @FXML
    private Button leftSlotBtn;
    @FXML
    private Button centerSlotBtn;
    @FXML
    private Button rightSlotBtn;
    @FXML
    private Button doneBtn;

    private ModelView modelView;

    public ProductionPopupSceneController(ModelView modelView){
        stage = new Stage();
        this.modelView = modelView;
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

        basicBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenBasicBtnClicked);
        leftSlotBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLeftSlotBtnClicked);
        centerSlotBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenCenterSlotBtnClicked);
        rightSlotBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRightSlotBtnClicked);
        doneBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDoneBtnClicked);
    }

    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    private void whenDoneBtnClicked(MouseEvent event){

    }

    private void whenBasicBtnClicked(MouseEvent event){

    }

    private void whenLeftSlotBtnClicked(MouseEvent event){

    }

    private void whenCenterSlotBtnClicked(MouseEvent event){

    }

    private void whenRightSlotBtnClicked(MouseEvent event){

    }

    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
