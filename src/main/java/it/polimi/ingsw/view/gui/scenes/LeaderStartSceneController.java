package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
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

public class LeaderStartSceneController extends ClientObservable implements GenericSceneController {

    private final Stage stage;
    private int index;
    private ArrayList<LeaderCard> leaderCards;
    private boolean first=true;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Button activateLeaderBtn1;
    @FXML
    private Button discardLeaderBtn1;
    @FXML
    private Button doneBtn1;
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

    public LeaderStartSceneController(){
        stage = new Stage();
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
        activateLeaderBtn1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenActivateLeaderButton1Clicked);
        discardLeaderBtn1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDiscardLeaderBtn1Clicked);
        doneBtn1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenDoneBtn1Clicked);
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


    }

    private void whenDiscardLeaderBtn1Clicked(MouseEvent event){


    }

    private void whenDoneBtn1Clicked(MouseEvent event){


    }

    private void whenActivateLeaderButton2Clicked(MouseEvent event){


    }

    private void whenDiscardLeaderBtn2Clicked(MouseEvent event){


    }

    private void whenDoneBtn2Clicked(MouseEvent event){

    }

    public void disableFirst(){
        activateLeaderBtn1.setDisable(true);
        discardLeaderBtn1.setDisable(true);
        doneBtn1.setDisable(true);
    }

    public void disableSecond(){
        activateLeaderBtn2.setDisable(true);
        discardLeaderBtn2.setDisable(true);
        doneBtn2.setDisable(true);
    }

    public ImageView setLeaderImage(LeaderCard leaderCard){

        ImageView leaderImage = new ImageView();
        Image image = new Image("/front/leader_" + leaderCard.getLeaderEffect() + "-" + leaderCard.getLeaderEffect().getObject());
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

