package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.BuyDevCard;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller of the slot popup, that it's used for placing bought devcards
 */
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

    /**
     * Constructor of the class.
     * @param modelView the current modelView for knowing if there are cards placed in a certain slot
     * @param cardIndexRow row of the card chosen in the shop
     * @param cardIndexColumn column of the card chosen in the shop
     */
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
        setImages();
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        leftSlot.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLeftSlotCLicked);
        centerSlot.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenCenterSlotClicked);
        rightSlot.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRightSlotClicked);
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
        stage.setX(event.getScreenX() + x_Offset);
        stage.setY(event.getScreenY() + y_Offset);
    }


    /**
     * These three methods ask the player where they want to place the devcard bought
     * @param event the event in the initialize method.
     */
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


    public void setImages(){
        if (modelView.getSlots()[0].getShowedCard() !=null){
            leftSlot.setImage(new Image("/front/devcard_color-"
                    + modelView.getSlots()[0].getShowedCard().getCardColor()
                    + "_level-" + modelView.getSlots()[0].getShowedCard().getLevel()
                    + "_vp-" + modelView.getSlots()[0].getShowedCard().getVictoryPointsDevCard() + ".png"));
        }

        if (modelView.getSlots()[1].getShowedCard() !=null){
            centerSlot.setImage(new Image("/front/devcard_color-"
                    + modelView.getSlots()[1].getShowedCard().getCardColor()
                    + "_level-" + modelView.getSlots()[1].getShowedCard().getLevel()
                    + "_vp-" + modelView.getSlots()[1].getShowedCard().getVictoryPointsDevCard() + ".png"));
        }

        if (modelView.getSlots()[2].getShowedCard() !=null){
            rightSlot.setImage(new Image("/front/devcard_color-"
                    + modelView.getSlots()[2].getShowedCard().getCardColor()
                    + "_level-" + modelView.getSlots()[2].getShowedCard().getLevel()
                    + "_vp-" + modelView.getSlots()[2].getShowedCard().getVictoryPointsDevCard() + ".png"));
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
