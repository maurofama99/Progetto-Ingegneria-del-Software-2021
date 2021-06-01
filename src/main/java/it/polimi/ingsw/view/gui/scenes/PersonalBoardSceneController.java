package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.resources.ResourceType;
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

    public PersonalBoardSceneController(ModelView modelView) {
        this.modelView = modelView;
    }

    public void initialize(){

        setDepotImage();

        leaderLeft.setImage(new Image("/back/leader-back.png"));
        leaderRight.setImage(new Image("/back/leader-back.png"));

        showDevCardsBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShowDevCardsBtnClicked);
        showMarketBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShowMarketBtnClicked);
    }


    private void whenShowDevCardsBtnClicked(MouseEvent event){
        Platform.runLater(() -> {
            DevcardPopupDisplayScene dpds = new DevcardPopupDisplayScene(modelView);
            dpds.addAllClientObservers(clientObservers);
            dpds.disableAll();
            SceneController.showPopup(dpds, "display_devcards.fxml");
        });
    }

    private void whenShowMarketBtnClicked(MouseEvent event){
        Platform.runLater(() -> {
            MarketPopupSceneController mpsc = new MarketPopupSceneController(modelView);
            mpsc.addAllClientObservers(clientObservers);
            mpsc.disable(true);
            SceneController.showPopup(mpsc, "market_tray_display.fxml");
        });
    }

    static void setResourceImage(String resource, ImageView spot) {
        switch (resource) {
            case "COIN":
                spot.setImage(new Image("/punchboard/resources/coin.png"));
                break;
            case "STONE":
                spot.setImage(new Image("/punchboard/resources/stone.png"));
                break;
            case "SERVANT":
                spot.setImage(new Image("/punchboard/resources/servant.png"));
                break;
            case "SHIELD":
                spot.setImage(new Image("/punchboard/resources/shield.png"));
                break;
        }
    }

    private void setDepotImage(){
        if (!modelView.getWarehouse().getFloors().get(0).getType().equals(ResourceType.NULLRESOURCE)){
            setResourceImage(modelView.getWarehouse().getFloors().get(0).toStringGui(), thirdLevel);
        }
        if (!modelView.getWarehouse().getFloors().get(1).getType().equals(ResourceType.NULLRESOURCE)){
            if (modelView.getWarehouse().getFloors().get(1).getQnt()==1)
                setResourceImage(modelView.getWarehouse().getFloors().get(1).toStringGui(), secondLevelLeft);
            else if ((modelView.getWarehouse().getFloors().get(1).getQnt()==2))
                setResourceImage(modelView.getWarehouse().getFloors().get(1).toStringGui(), secondLevelLeft);
                setResourceImage(modelView.getWarehouse().getFloors().get(1).toStringGui(), secondLevelRight);
        }
        if (!modelView.getWarehouse().getFloors().get(2).getType().equals(ResourceType.NULLRESOURCE)){
            if (modelView.getWarehouse().getFloors().get(2).getQnt()==1)
                setResourceImage(modelView.getWarehouse().getFloors().get(2).toStringGui(), firstLevelLeft);
            else if (modelView.getWarehouse().getFloors().get(2).getQnt()==2){
                setResourceImage(modelView.getWarehouse().getFloors().get(2).toStringGui(), firstLevelRight);
                setResourceImage(modelView.getWarehouse().getFloors().get(2).toStringGui(), firstLevelLeft);
            }
            else {
                setResourceImage(modelView.getWarehouse().getFloors().get(2).toStringGui(), firstLevelRight);
                setResourceImage(modelView.getWarehouse().getFloors().get(2).toStringGui(), firstLevelLeft);
                setResourceImage(modelView.getWarehouse().getFloors().get(2).toStringGui(), firstLevelCenter);
            }
        }
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
