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

    //TODO: assigning inkwell to first player
    //TODO: blackcross for singleplayer
    private ModelView modelView;

    @FXML
    private ImageView inkwell;
    //Depot
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
    //End of depot
    //Slots
    @FXML
    private ImageView slotLeft;
    @FXML
    private ImageView slotCenter;
    @FXML
    private ImageView slotRight;
    //End of slots
    //FaithTrack
    @FXML
    private ImageView faithCross;
    @FXML
    private ImageView tile_1;
    @FXML
    private ImageView tile_2;
    @FXML
    private ImageView tile_3;
    @FXML
    private ImageView tile_4;
    @FXML
    private ImageView tile_5;
    @FXML
    private ImageView tile_6;
    @FXML
    private ImageView tile_7;
    @FXML
    private ImageView tile_8;
    @FXML
    private ImageView tile_9;
    @FXML
    private ImageView tile_10;
    @FXML
    private ImageView tile_11;
    @FXML
    private ImageView tile_12;
    @FXML
    private ImageView tile_13;
    @FXML
    private ImageView tile_14;
    @FXML
    private ImageView tile_15;
    @FXML
    private ImageView tile_16;
    @FXML
    private ImageView tile_17;
    @FXML
    private ImageView tile_18;
    @FXML
    private ImageView tile_19;
    @FXML
    private ImageView tile_20;
    @FXML
    private ImageView tile_21;
    @FXML
    private ImageView tile_22;
    @FXML
    private ImageView tile_23;
    @FXML
    private ImageView tile_24;
    @FXML
    private ImageView firstPopeTile;
    @FXML
    private ImageView secondPopeTile;
    @FXML
    private ImageView thirdPopeTile;
    //End of FaithTrack
    @FXML
    private Button showMarketBtn;
    @FXML
    private Button showDevCardsBtn;
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
            case "STONE":
                spot.setImage(new Image("/punchboard/resources/stone.png"));
            case "SERVANT":
                spot.setImage(new Image("/punchboard/resources/servant.png"));
            case "SHIELD":
                spot.setImage(new Image("/punchboard/resources/shield.png"));
        }
    }

    private void setDepotImage(){
        if (!modelView.getWarehouse().getFloors().get(0).getType().equals(ResourceType.NULLRESOURCE)){
            setResourceImage(modelView.getWarehouse().getFloors().get(0).toStringGui(), thirdLevel);
        }
        if (!modelView.getWarehouse().getFloors().get(1).getType().equals(ResourceType.NULLRESOURCE)){
            if (modelView.getWarehouse().getFloors().get(1).getQnt()==1)
                setResourceImage(modelView.getWarehouse().getFloors().get(1).toStringGui(), secondLevelLeft);
            else
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


    public Image setCross(){
        return new Image("/punchboard/croce.png");
    }

    public void setCrossInTile(int faithPoints){
        String tile = "tile_" + Integer.toString(faithPoints);

    }

}
