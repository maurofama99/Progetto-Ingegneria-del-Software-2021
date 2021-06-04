package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.messagescs.DiscardLeader;
import it.polimi.ingsw.observerPattern.ClientObservable;
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

    ArrayList<LeaderCard> leaderCards = new ArrayList<>();

    private int index1;
    private boolean first = true;

    @FXML
    private StackPane rootPane;
    @FXML
    private Button firstBtn;
    @FXML
    private Button secondBtn;
    @FXML
    private Button thirdBtn;
    @FXML
    private Button fourthBtn;
    @FXML
    private ImageView firstLeader;
    @FXML
    private ImageView secondLeader;
    @FXML
    private ImageView thirdLeader;
    @FXML
    private ImageView fourthLeader;


    @FXML
    public void initialize(){
        setLeaderCardsImages();
        firstBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstClicked);
        secondBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondClicked);
        thirdBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenThirdClicked);
        fourthBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFourthClicked);
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
            firstBtn.setDisable(true);
        } else {
            firstBtn.setDisable(true);
            secondBtn.setDisable(true);
            thirdBtn.setDisable(true);
            fourthBtn.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 0));
        }
    }

    private void whenSecondClicked(MouseEvent event){
        if (first) {
            index1 = 1;
            first = false;
            secondBtn.setDisable(true);
        } else {
            firstBtn.setDisable(true);
            secondBtn.setDisable(true);
            thirdBtn.setDisable(true);
            fourthBtn.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 1));
        }
    }

    private void whenThirdClicked(MouseEvent event){
        if (first) {
            index1 = 2;
            first = false;
            thirdBtn.setDisable(true);
        } else {
            firstBtn.setDisable(true);
            secondBtn.setDisable(true);
            thirdBtn.setDisable(true);
            fourthBtn.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 2));
        }
    }

    private void whenFourthClicked(MouseEvent event){
        if (first) {
            index1 = 3;
            first = false;
            fourthBtn.setDisable(true);
        } else {
            firstBtn.setDisable(true);
            secondBtn.setDisable(true);
            thirdBtn.setDisable(true);
            fourthBtn.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 3));
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



}
