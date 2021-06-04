package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class ShowOthersPopupController extends ClientObservable implements GenericPopupController {

    private Stage stage;
    private final String player;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private StackPane rootPane;

    private ModelView modelView;
    @FXML
    private ImageView inkwell;
    //Depot
    @FXML
    private ImageView firstLevelLeft, firstLevelCenter, firstLevelRight, secondLevelLeft;
    @FXML
    private ImageView secondLevelRight, thirdLevel;
    //End of depot
    //Slots
    @FXML
    private ImageView slotLeft1, slotLeft2, slotLeft3;
    @FXML
    private ImageView slotCenter1, slotCenter2, slotCenter3;
    @FXML
    private ImageView slotRight1, slotRight2, slotRight3;
    //End of slots
    //FaithTrack
    @FXML
    private ImageView faithCross;
    @FXML
    private ImageView tile_0, tile_1, tile_2, tile_3, tile_4, tile_5, tile_6, tile_7, tile_8 ,tile_9;
    @FXML
    private ImageView tile_10 ,tile_11, tile_12, tile_13, tile_14 ,tile_15 ,tile_16 ,tile_17;
    @FXML
    private ImageView tile_18 ,tile_19, tile_20, tile_21, tile_22, tile_23 ,tile_24;
    @FXML
    private ImageView firstPopeTile, secondPopeTile, thirdPopeTile;
    //End of FaithTrack
    @FXML
    private Button showMarketBtn, showDevCardsBtn, firstPlayerBtn, secondPlayerBtn, thirdPlayerBtn;
    @FXML
    private ImageView leaderLeft, leaderRight;
    @FXML
    private Label coinCounter, stoneCounter, servantCounter, shieldCounter;

    public int coinQnt = 0;
    public int stoneQnt = 0;
    public int servantQnt = 0;
    public int shieldQnt = 0;
    @FXML
    private Label nickname;
    @FXML
    private Button closeBtn;

    public ShowOthersPopupController(String nickname) {
        this.player = nickname;
        stage = new Stage();
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
    }

    public void initialize(){
        setDepotImage();
        setFaithTrackImages();
        setSlotImages();

        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenCloseBtnClicked);
    }

    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    private void whenCloseBtnClicked(MouseEvent event){
        stage.close();
    }

    static void setResourceImage(String resource, ImageView spot) {
        spot.setImage(new Image("/punchboard/resources/"+resource+".png"));
    }

    private void setDepotImage(){
        if (!modelView.getWarehouse().getFloors().get(0).getType().equals(ResourceType.NULLRESOURCE)){
            setResourceImage(modelView.getWarehouse().getFloors().get(0).toStringGui(), thirdLevel);
        }
        if (!modelView.getWarehouse().getFloors().get(1).getType().equals(ResourceType.NULLRESOURCE)){
            if (modelView.getWarehouse().getFloors().get(1).getQnt()==1)
                setResourceImage(modelView.getWarehouse().getFloors().get(1).toStringGui(), secondLevelLeft);
            else if ((modelView.getWarehouse().getFloors().get(1).getQnt()==2)) {
                setResourceImage(modelView.getWarehouse().getFloors().get(1).toStringGui(), secondLevelLeft);
                setResourceImage(modelView.getWarehouse().getFloors().get(1).toStringGui(), secondLevelRight);
            }
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


    public void setFaithTrackImages() {

        if (modelView.getFaithTrack().isFirstFavorTile()){
            firstPopeTile.setImage(new Image("/punchboard/tile_2_front.png"));
        }
        else
            firstPopeTile.setImage(new Image("/punchboard/tile_2_back.png"));

        if (modelView.getFaithTrack().isSecondFavorTile()){
            secondPopeTile.setImage(new Image("/punchboard/tile_3_front.png"));
        }
        else
            secondPopeTile.setImage(new Image("/punchboard/tile_3_back.png"));

        if (modelView.getFaithTrack().isThirdFavorTile()){
            thirdPopeTile.setImage(new Image("/punchboard/tile_4_front.png"));
        }
        else
            thirdPopeTile.setImage(new Image("/punchboard/tile_4_back.png"));




        ArrayList<ImageView> imageViews = new ArrayList<>();
        imageViews.add(tile_0);
        imageViews.add(tile_1);
        imageViews.add(tile_2);
        imageViews.add(tile_3);
        imageViews.add(tile_4);
        imageViews.add(tile_5);
        imageViews.add(tile_6);
        imageViews.add(tile_7);
        imageViews.add(tile_8);
        imageViews.add(tile_9);
        imageViews.add(tile_10);
        imageViews.add(tile_11);
        imageViews.add(tile_12);
        imageViews.add(tile_13);
        imageViews.add(tile_14);
        imageViews.add(tile_15);
        imageViews.add(tile_16);
        imageViews.add(tile_17);
        imageViews.add(tile_18);
        imageViews.add(tile_19);
        imageViews.add(tile_20);
        imageViews.add(tile_21);
        imageViews.add(tile_22);
        imageViews.add(tile_23);
        imageViews.add(tile_24);

        imageViews.get(modelView.getFaithTrack().getFaithMarkerPosition()).setImage(new Image("/punchboard/croce.png"));



    }

    public void setSlotImages(){
        for (int i =0; i<3; i++){
            if (modelView.getSlots()[i].getShowedCard() !=null){
                slotLeft1.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[i].getShowedCard().getCardColor()
                        + "_level-" + modelView.getSlots()[i].getShowedCard().getLevel()
                        + "_vp-" + modelView.getSlots()[i].getShowedCard().getVictoryPointsDevCard() + ".png"));
            }
        }

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

    public void showPopUp(){
        stage.show();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
