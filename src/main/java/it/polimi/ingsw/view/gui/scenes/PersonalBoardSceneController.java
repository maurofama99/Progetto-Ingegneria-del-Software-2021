package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Path;

public class PersonalBoardSceneController extends ClientObservable implements GenericSceneController {

    //TODO: moving faithmarker and path ffs
    //TODO: assigning inkwell to first player
    //TODO: blackcross for singleplayer
    private ModelView modelView;

    @FXML
    private ImageView inkwell;
    @FXML
    private Path faithTrackPath;
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

    public int coinQnt = 0;
    public int stoneQnt = 0;
    public int servantQnt = 0;
    public int shieldQnt = 0;

    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }

    public void initialize(){

        baseProdBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenBaseProdBtnClicked);
        prod1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenProd1BtnClicked);
        prod2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenProd2BtnClicked);
        prod3Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenProd3BtnClicked);
        showDevCardsBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShowDevCardsBtnClicked);
        showMarketBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShowMarketBtnClicked);
        leader1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLeader1BtnClicked);
        leader2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenLeader2BtnClicked);
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
        Platform.runLater(() -> {
            MarketPopupSceneController mpsc = new MarketPopupSceneController(modelView);
            mpsc.addAllClientObservers(clientObservers);
            mpsc.disableAll();
            SceneController.showPopup(mpsc, "market_tray_display.fxml");
        });
    }

    private void whenLeader1BtnClicked(MouseEvent event){

    }

    private void whenLeader2BtnClicked(MouseEvent event){

    }

    static void setImageDepositSpot(String resource, ImageView spot) {
        switch (resource) {
            case "coin":
                Image coin = new Image("/punchboard/resources/coin.png");
                spot.setImage(coin);
            case "stone":
                Image stone = new Image("/punchboard/resources/stone.png");
                spot.setImage(stone);
            case "servant":
                Image servant = new Image("/punchboard/resources/servant.png");
                spot.setImage(servant);
            case "shield":
                Image shield = new Image("/punchboard/resources/shield.png");
                spot.setImage(shield);
        }
    }



    public void setFirstLevelLeft(String resource) {
        setImageDepositSpot(resource, firstLevelLeft);
    }

    public void setFirstLevelCenter(String resource) {
        setImageDepositSpot(resource, firstLevelCenter);
    }

    public void setFirstLevelRight(String resource) {
        setImageDepositSpot(resource, firstLevelRight);
    }

    public void setSecondLevelLeft(String resource) {
        setImageDepositSpot(resource, secondLevelLeft);
    }

    public void setSecondLevelRight(String resource) {
        setImageDepositSpot(resource, secondLevelRight);
    }

    public void setThirdLevel(String resource) {
        setImageDepositSpot(resource, thirdLevel);
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

    public void increaseCounter(Label labelToSet, int quantity, int resourcePresent){
        resourcePresent = resourcePresent+quantity;
        labelToSet.setText("x"+Integer.toString(resourcePresent));
    }

    public void decreaseCounter(Label labelToSet, int quantity, int resourcePresent){
        resourcePresent = resourcePresent-quantity;
        labelToSet.setText("x"+Integer.toString(resourcePresent));
    }

    public void increaseCoinCounter(int quantity){
        increaseCounter(coinCounter, quantity, coinQnt);
    }

    public void decreaseCoinCounter(int quantity){
        decreaseCounter(coinCounter, quantity, coinQnt);
    }

    public void increaseStoneCounter(int quantity){
        increaseCounter(stoneCounter, quantity, stoneQnt);
    }

    public void decreaseStoneCounter(int quantity){
        decreaseCounter(stoneCounter, quantity, stoneQnt);
    }

    public void increaseServantCounter(int quantity){
        increaseCounter(servantCounter, quantity, servantQnt);
    }

    public void decreaseServantCounter(int quantity){
        decreaseCounter(servantCounter, quantity, servantQnt);
    }

    public void increaseShieldCounter(int quantity){
        increaseCounter(shieldCounter, quantity, shieldQnt);
    }

    public void decreaseShieldCounter(int quantity){
        decreaseCounter(shieldCounter, quantity, shieldQnt);
    }


}
