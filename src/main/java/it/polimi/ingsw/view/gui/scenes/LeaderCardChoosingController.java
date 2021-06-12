package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.messagescs.DiscardLeader;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * Controller for the scene where the player chooses his ledaer cards
 */
public class LeaderCardChoosingController extends ClientObservable implements GenericSceneController {

    private ArrayList<LeaderCard> leaderCards = new ArrayList<>();
    private boolean firstPlayer;
    private int index1;
    private boolean first;
    private boolean solo;

    public LeaderCardChoosingController(boolean firstPlayer, boolean solo) {
        this.firstPlayer = firstPlayer;
        this.solo = solo;
    }

    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView firstLeader, secondLeader, thirdLeader, fourthLeader;


    @FXML
    public void initialize(){
        setLeaderCardsImages();
        firstLeader.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstClicked);
        secondLeader.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondClicked);
        thirdLeader.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenThirdClicked);
        fourthLeader.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFourthClicked);
    }

    /**
     * Sets the leader cards
     * @param leaderCards the arraylist of leader cards in the message
     */
    public void setLeaderCards(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    /**
     * Four methods to handle the discard action. They check the index to know if it is the first click or the second
     * @param event the event from the mouse in the initialize method
     */
    private void whenFirstClicked(MouseEvent event){
        if (first) {
            index1 = 0;
            first = false;
            firstLeader.setDisable(true);
            firstLeader.setOpacity(0.6);
        } else {
            firstLeader.setOpacity(0.6);
            firstLeader.setDisable(true);
            secondLeader.setDisable(true);
            thirdLeader.setDisable(true);
            fourthLeader.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 0));
            if (!firstPlayer && !solo) waiting();
        }
    }

    private void whenSecondClicked(MouseEvent event){
        if (first) {
            index1 = 1;
            first = false;
            secondLeader.setOpacity(0.6);
            secondLeader.setDisable(true);
        } else {
            secondLeader.setOpacity(0.6);
            firstLeader.setDisable(true);
            secondLeader.setDisable(true);
            thirdLeader.setDisable(true);
            fourthLeader.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 1));
            if (!firstPlayer && !solo) waiting();
        }
    }

    private void whenThirdClicked(MouseEvent event){
        if (first) {
            index1 = 2;
            first = false;
            thirdLeader.setOpacity(0.6);
            thirdLeader.setDisable(true);
        } else {
            thirdLeader.setOpacity(0.6);
            firstLeader.setDisable(true);
            secondLeader.setDisable(true);
            thirdLeader.setDisable(true);
            fourthLeader.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 2));
            if (!firstPlayer && !solo) waiting();
        }
    }

    private void whenFourthClicked(MouseEvent event){
        if (first) {
            index1 = 3;
            first = false;
            fourthLeader.setOpacity(0.6);
            fourthLeader.setDisable(true);
        } else {
            fourthLeader.setOpacity(0.6);
            firstLeader.setDisable(true);
            secondLeader.setDisable(true);
            thirdLeader.setDisable(true);
            fourthLeader.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 3));
            if (!firstPlayer && !solo) waiting();
        }

    }

    /**
     * Sets the image for the leader card
     * @param leaderCard the leaderCard from where we take attributes to create the name we will match in the
     *                   resources folder
     * @return an Image that will be set
     */
    public Image setLeaderImage(LeaderCard leaderCard){
        return new Image("/front/leader_" + leaderCard.getLeaderEffect().toString() + ".png");
    }

    /**
     * Sets the images of the four leader cards
     */
    public void setLeaderCardsImages(){
        firstLeader.setImage(setLeaderImage(leaderCards.get(0)));
        secondLeader.setImage(setLeaderImage(leaderCards.get(1)));
        thirdLeader.setImage(setLeaderImage(leaderCards.get(2)));
        fourthLeader.setImage(setLeaderImage(leaderCards.get(3)));
    }

    public void waiting(){
        Platform.runLater(()->{
            PopupSceneController psc = new PopupSceneController("Please wait for your turn, your opponents are now playing their first turn.");
            psc.addAllClientObservers(clientObservers);
            SceneController.showPopup(psc, "popup_scene.fxml");
        });
    }



}
