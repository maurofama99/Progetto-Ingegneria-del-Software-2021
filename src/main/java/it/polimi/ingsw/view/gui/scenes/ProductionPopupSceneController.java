package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.ActivateProduction;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Arrays;


public class ProductionPopupSceneController extends ClientObservable implements GenericPopupController {

    private final Stage stage;

    private final ArrayList<Integer> productions = new ArrayList<>(Arrays.asList(0, 0, 0, 0));

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private BorderPane rootPane;
    @FXML
    private ImageView leftSlot, centerSlot, rightSlot;
    @FXML
    private CheckBox basicBtn, leftSlotBtn, centerSlotBtn, rightSlotBtn;
    @FXML
    private Button doneBtn = new Button();

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

        /*basicBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenBasicBtnClicked);
        leftSlotBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLeftSlotBtnClicked);
        centerSlotBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenCenterSlotBtnClicked);
        rightSlotBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRightSlotBtnClicked);*/
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
        if (basicBtn.isSelected()) productions.set(0,1);
        if (leftSlotBtn.isSelected()) productions.set(1,1);
        if (centerSlotBtn.isSelected()) productions.set(2,1);
        if (rightSlotBtn.isSelected()) productions.set(3,1);
        notifyObservers(new ActivateProduction(productions.get(0), productions.get(1), productions.get(2), productions.get(3)));
        stage.close();
    }

    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
