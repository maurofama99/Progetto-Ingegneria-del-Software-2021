package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.messagescs.ActivateLeader;
import it.polimi.ingsw.network.messagescs.DiscardOneLeader;
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

public class LeaderStartSceneController extends ClientObservable implements GenericPopupController {

    private Stage stage;
    private ModelView modelView;
    private int index;
    private ArrayList<LeaderCard> leaderCards;
    private boolean first=true;
    private boolean isEndTurn;

    public LeaderStartSceneController(ArrayList<LeaderCard> leaderCards, boolean isEndTurn, ModelView modelView) {
        this.leaderCards = leaderCards;
        this.isEndTurn = isEndTurn;
        this.modelView = modelView;
        stage = new Stage();
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
    }

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Button activateLeaderBtn1;
    @FXML
    private Button discardLeaderBtn1;
    @FXML
    private Button activateLeaderBtn2;
    @FXML
    private Button discardLeaderBtn2;
    @FXML
    private Button doneBtn2;
    @FXML
    private ImageView leader1;
    @FXML
    private ImageView leader2;

    @FXML
    public void initialize(){
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        activateLeaderBtn1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenActivateLeaderButton1Clicked);
        discardLeaderBtn1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDiscardLeaderBtn1Clicked);
        activateLeaderBtn2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenActivateLeaderButton2Clicked);
        discardLeaderBtn2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDiscardLeaderBtn2Clicked);
        doneBtn2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDoneBtn2Clicked);
    }

    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    private void whenActivateLeaderButton1Clicked(MouseEvent event){
        notifyObservers(new ActivateLeader(0));
        disableAll();
    }

    private void whenDiscardLeaderBtn1Clicked(MouseEvent event){
        notifyObservers(new DiscardOneLeader(0));
        disableAll();
    }

    private void whenActivateLeaderButton2Clicked(MouseEvent event){
        notifyObservers(new ActivateLeader(1));
        disableAll();
    }

    private void whenDiscardLeaderBtn2Clicked(MouseEvent event){
        notifyObservers(new DiscardOneLeader(1));
        disableAll();
    }

    private void whenDoneBtn2Clicked(MouseEvent event){

        if (!isEndTurn) {
            ActionPopupController apc = new ActionPopupController(modelView);
            apc.addAllClientObservers(clientObservers);
            Platform.runLater(() -> SceneController.showPopup(apc, "action_popup.fxml"));
        }
        //else
            //fetchDoneAction
        stage.close();

    }

    public void disableAll(){
        activateLeaderBtn1.setDisable(true);
        discardLeaderBtn1.setDisable(true);
        activateLeaderBtn2.setDisable(true);
        discardLeaderBtn2.setDisable(true);
        doneBtn2.setDisable(true);
    }


    public ImageView setLeaderImage(LeaderCard leaderCard){

        ImageView leaderImage = new ImageView();
        Image image = new Image("/front/leader_" + leaderCard.getLeaderEffect());
        leaderImage.setImage(image);

        return leaderImage;
    }

    public void setImages(){
        ArrayList<ImageView> leaderCardsImages = new ArrayList<>();
        int z=0;

        for (int i = 0; i <2 ; i++) {
            leaderCardsImages.add(setLeaderImage(leaderCards.get(i)));
        }

        leader1 = leaderCardsImages.get(0);
        leader2 = leaderCardsImages.get(1);
    }

    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}

