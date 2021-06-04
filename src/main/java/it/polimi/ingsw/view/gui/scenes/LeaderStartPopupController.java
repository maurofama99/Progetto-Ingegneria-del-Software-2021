package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.messagescs.ActivateLeader;
import it.polimi.ingsw.network.messagescs.DiscardOneLeader;
import it.polimi.ingsw.network.messagescs.DoneAction;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

/**
 * Popup at the start and end of each turn, that asks if the player wants to activate a leader card
 */
public class LeaderStartPopupController extends ClientObservable implements GenericPopupController {

    @FXML
    private BorderPane rootPane;
    @FXML
    private Button activateLeaderBtn1, discardLeaderBtn1, activateLeaderBtn2, discardLeaderBtn2;
    @FXML
    private Button doneBtn2;
    @FXML
    private ImageView leader1, leader2;

    @FXML
    public void initialize(){
        setImages();
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        activateLeaderBtn1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenActivateLeaderButton1Clicked);
        discardLeaderBtn1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDiscardLeaderBtn1Clicked);
        activateLeaderBtn2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenActivateLeaderButton2Clicked);
        discardLeaderBtn2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDiscardLeaderBtn2Clicked);
        doneBtn2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDoneBtn2Clicked);
    }

    private Stage stage;
    private ModelView modelView;
    private ArrayList<LeaderCard> leaderCards;
    private boolean first=true;
    private boolean isEndTurn;

    /**
     * Constructor of the class
     * @param leaderCards the two leader cards of the player
     * @param isEndTurn check if it is the end of the turn
     * @param modelView the updated model view
     */
    public LeaderStartPopupController(ArrayList<LeaderCard> leaderCards, boolean isEndTurn, ModelView modelView) {
        this.leaderCards = leaderCards;
        this.isEndTurn = isEndTurn;
        this.modelView = modelView;
        stage = new Stage();
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.NONE);
        //stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
    }

    private double x_Offset;
    private double y_Offset;



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
        stage.setY(event.getSceneY() +y_Offset);
    }

    /**
     * Check the requirements and activates the first leader card
     * @param event the event in the initialize
     */
    private void whenActivateLeaderButton1Clicked(MouseEvent event){
        notifyObservers(new ActivateLeader(0, isEndTurn));
        disableAll();
        stage.close();
    }

    /**
     * discards the first leader card and gives a faithpoint
     * @param event the event in the initialize
     */
    private void whenDiscardLeaderBtn1Clicked(MouseEvent event){
        notifyObservers(new DiscardOneLeader(0, isEndTurn));
        disableAll();
        stage.close();
    }

    /**
     * Check the requirements and activates the second leader card
     * @param event the event in the initialize
     */
    private void whenActivateLeaderButton2Clicked(MouseEvent event){
        notifyObservers(new ActivateLeader(1, isEndTurn));
        disableAll();
        stage.close();
    }

    /**
     * discards the second leader card and gives a faith point
     * @param event the event in the initialize
     */
    private void whenDiscardLeaderBtn2Clicked(MouseEvent event){
        notifyObservers(new DiscardOneLeader(1, isEndTurn));
        disableAll();
        stage.close();
    }

    /**
     * Closes the popup
     * @param event the event in the initialize
     */
    private void whenDoneBtn2Clicked(MouseEvent event){

        if (!isEndTurn) {
            ActionPopupController apc = new ActionPopupController(modelView);
            apc.addAllClientObservers(clientObservers);
            Platform.runLater(() -> SceneController.showPopup(apc, "action_popup.fxml"));
        }
        else
            notifyObservers(new DoneAction());
        stage.close();

    }

    /**
     * Disables all buttons two prevent double input
     */
    public void disableAll(){
        activateLeaderBtn1.setDisable(true);
        discardLeaderBtn1.setDisable(true);
        activateLeaderBtn2.setDisable(true);
        discardLeaderBtn2.setDisable(true);
    }

    /**
     * Same as the LeaderCardChoosingController, these two methods sets the images of the cards
     * @param leaderCard the leader card to set
     * @return an Image of the leader card
     */
    public Image setLeaderImage(LeaderCard leaderCard){
        return new Image("/front/leader_" + leaderCard.getLeaderEffect()+".png");
    }

    public void setImages(){
        if (leaderCards.size()==0){
            disableAll();
        }
        else if (leaderCards.size()==1){
            leader1.setImage(setLeaderImage(leaderCards.get(0)));
            activateLeaderBtn2.setDisable(true);
            discardLeaderBtn2.setDisable(true);
        }
        else {
            leader1.setImage(setLeaderImage(leaderCards.get(0)));
            leader2.setImage(setLeaderImage(leaderCards.get(1)));
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

    /*
    if (modelView.getLeaderCards().get(0) == null){
            leader1.setImage(new Image("/back/leader-back.png"));
            leader2.setImage(new Image("/back/leader-back.png"));
        }
        else if (modelView.getLeaderCards().get(1) == null){
            leader1.setImage(setLeaderImage(modelView.getLeaderCards().get(0)));
            leader2.setImage(new Image("/back/leader-back.png"));
        }
        else{
            leader2.setImage(setLeaderImage(modelView.getLeaderCards().get(1)));
        }
     */
}

