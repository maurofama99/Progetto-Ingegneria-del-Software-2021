package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.BuyDevCard;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SlotSelectPopupController extends ClientObservable implements GenericPopupController {
    @FXML
    private BorderPane rootPane;
    @FXML
    private ImageView leftSlot, centerSlot, rightSlot;

    private final Stage stage;
    private ModelView modelView;
    private int cardIndexRow;
    private int cardIndexColumn;

    private double x_Offset;
    private double y_Offset;

    public SlotSelectPopupController(ModelView modelView, int cardIndexRow, int cardIndexColumn){
        stage = new Stage();
        this.modelView = modelView;
        this.cardIndexRow = cardIndexRow;
        this.cardIndexColumn = cardIndexColumn;
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.NONE);
        //stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
    }

    @FXML
    public void initialize(){
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        leftSlot.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLeftSlotCLicked);
        centerSlot.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenCenterSlotClicked);
        rightSlot.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRightSlotClicked);
    }

    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    private void whenLeftSlotCLicked(MouseEvent event){
        notifyObservers(new BuyDevCard(cardIndexRow, cardIndexColumn, 1));
        stage.close();
    }

    private void whenCenterSlotClicked(MouseEvent event){
        notifyObservers(new BuyDevCard(cardIndexRow, cardIndexColumn, 2));
        stage.close();
    }

    private void whenRightSlotClicked(MouseEvent event){
        notifyObservers(new BuyDevCard(cardIndexRow, cardIndexColumn, 3));
        stage.close();
    }

    public void showPopUp(){
        stage.show();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }


}
