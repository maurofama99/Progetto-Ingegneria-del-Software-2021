package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PersonalBoardSceneController extends ClientObservable implements GenericSceneController {
    @FXML
    private ImageView firstLevelLeft;
    @FXML
    private ImageView firstLevelCenter;
    @FXML
    private ImageView firstLevelRight;
    @FXML
    private ImageView secondLevelLeft;
    @FXML
    private ImageView secondLevelRight;
    @FXML
    private ImageView thirdLevel;
    @FXML
    private ImageView faithCross;
    @FXML
    private ImageView slotLeft;
    @FXML
    private ImageView slotCenter;
    @FXML
    private ImageView slotRight;
    @FXML
    private ImageView firstPopeTile;
    @FXML
    private ImageView secondPopeTile;
    @FXML
    private ImageView thirdPopeTile;
    @FXML
    private Button switchBtn;
    @FXML
    private Button baseProdBtn;
    @FXML
    private Button prod1Btn;
    @FXML
    private Button prod2Btn;
    @FXML
    private Button prod3Btn;
    @FXML
    private Button showMarketBtn; //Popup needed
    @FXML
    private Button showDevCardsBtn; //Popup needed
    @FXML
    private Button leader1Btn; //Popup needed
    @FXML
    private Button leader2Btn; //Popup needed
    @FXML
    private ImageView leaderLeft;
    @FXML
    private ImageView leaderRight;
    @FXML
    private Label coinCounter;
    @FXML
    private Label stoneCounter;
    @FXML
    private Label servantCounter;
    @FXML
    private Label shieldCounter;


    public void initialize(){

        switchBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSwitchBtnClicked);
        baseProdBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenBaseProdBtnClicked);
        prod1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenProd1BtnClicked);
        prod2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenProd2BtnClicked);
        prod3Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenProd3BtnClicked);
        showDevCardsBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShowDevCardsBtnClicked);
        showMarketBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShowMarketBtnClicked);
        leader1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLeader1BtnClicked);
        leader2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLeader2BtnClicked);
    }

    private void whenSwitchBtnClicked(MouseEvent event){

    }

    private void whenBaseProdBtnClicked(MouseEvent event){

    }

    private void whenProd1BtnClicked(MouseEvent event){

    }

    private void whenProd2BtnClicked(MouseEvent event){

    }

    private void whenProd3BtnClicked(MouseEvent event){

    }

    private void whenShowDevCardsBtnClicked(MouseEvent event){

    }

    private void whenShowMarketBtnClicked(MouseEvent event){

    }

    private void whenLeader1BtnClicked(MouseEvent event){

    }

    private void whenLeader2BtnClicked(MouseEvent event){

    }

    public void setFirstLevelLeft(ImageView firstLevelLeft) {
        this.firstLevelLeft = firstLevelLeft;
    }

    public void setFirstLevelCenter(ImageView firstLevelCenter) {
        this.firstLevelCenter = firstLevelCenter;
    }

    public void setFirstLevelRight(ImageView firstLevelRight) {
        this.firstLevelRight = firstLevelRight;
    }

    public void setSecondLevelLeft(ImageView secondLevelLeft) {
        this.secondLevelLeft = secondLevelLeft;
    }

    public void setSecondLevelRight(ImageView secondLevelRight) {
        this.secondLevelRight = secondLevelRight;
    }

    public void setThirdLevel(ImageView thirdLevel) {
        this.thirdLevel = thirdLevel;
    }

    public void setSlotLeft(ImageView slotLeft) {
        this.slotLeft = slotLeft;
    }

    public void setSlotCenter(ImageView slotCenter) {
        this.slotCenter = slotCenter;
    }

    public void setSlotRight(ImageView slotRight) {
        this.slotRight = slotRight;
    }

    public void setFirstPopeTile(ImageView firstPopeTile) {
        this.firstPopeTile = firstPopeTile;
    }

    public void setSecondPopeTile(ImageView secondPopeTile) {
        this.secondPopeTile = secondPopeTile;
    }

    public void setThirdPopeTile(ImageView thirdPopeTile) {
        this.thirdPopeTile = thirdPopeTile;
    }

    public void setLeaderLeft(ImageView leaderLeft) {
        this.leaderLeft = leaderLeft;
    }

    public void setLeaderRight(ImageView leaderRight) {
        this.leaderRight = leaderRight;
    }

    public void setCoinCounter(Label coinCounter) {
        this.coinCounter = coinCounter;
    }

    public void setStoneCounter(Label stoneCounter) {
        this.stoneCounter = stoneCounter;
    }

    public void setServantCounter(Label servantCounter) {
        this.servantCounter = servantCounter;
    }

    public void setShieldCounter(Label shieldCounter) {
        this.shieldCounter = shieldCounter;
    }
}
