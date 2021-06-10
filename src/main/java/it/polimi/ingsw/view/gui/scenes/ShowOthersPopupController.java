package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
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
    private ModelView modelView;
    private ResourceType NResourceType = ResourceType.NULLRESOURCE;// upper card resource type if extra depot
    private ResourceType SResourceType = ResourceType.NULLRESOURCE;// lower card resource type if extra depot

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private StackPane rootPane;


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
    private ImageView leaderLeft, NEResource, NWResource;
    @FXML
    private ImageView leaderRight, SEResource, SWResource;
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

    public ShowOthersPopupController(String nickname, ModelView modelView) {
        this.player = nickname;
        this.modelView = modelView;
        stage = new Stage();
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
        this.nickname.setText(nickname);

        for (LeaderCard leaderCard : modelView.getOthersPersonalBoards().get(player).getActiveLeaderCards()){
            if (leaderCard.getLeaderEffect().getEffectType() == EffectType.EXTRADEPOT){
                if (modelView.getOthersPersonalBoards().get(player).getActiveLeaderCards().indexOf(leaderCard) == 0){
                    NResourceType = (ResourceType) leaderCard.getLeaderEffect().getObject();
                } else {
                    SResourceType = (ResourceType) leaderCard.getLeaderEffect().getObject();
                }
            }
        }
    }

    public void initialize(){
        setDepotImage();
        setFaithTrackImages();
        setSlotImages();
        setExtraDepotImages();
        setImagesStrongBox();
        setLeaderImages();

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
        if (!modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(0).getType().equals(ResourceType.NULLRESOURCE)){
            setResourceImage(modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(0).toStringGui(), thirdLevel);
        }
        if (!modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(1).getType().equals(ResourceType.NULLRESOURCE)){
            if (modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(1).getQnt()==1)
                setResourceImage(modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(1).toStringGui(), secondLevelLeft);
            else if ((modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(1).getQnt()==2)) {
                setResourceImage(modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(1).toStringGui(), secondLevelLeft);
                setResourceImage(modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(1).toStringGui(), secondLevelRight);
            }
        }
        if (!modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(2).getType().equals(ResourceType.NULLRESOURCE)){
            if (modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(2).getQnt()==1)
                setResourceImage(modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(2).toStringGui(), firstLevelLeft);
            else if (modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(2).getQnt()==2){
                setResourceImage(modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(2).toStringGui(), firstLevelRight);
                setResourceImage(modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(2).toStringGui(), firstLevelLeft);
            }
            else {
                setResourceImage(modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(2).toStringGui(), firstLevelRight);
                setResourceImage(modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(2).toStringGui(), firstLevelLeft);
                setResourceImage(modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getFloors().get(2).toStringGui(), firstLevelCenter);
            }
        }
    }


    public void setFaithTrackImages() {

        if (modelView.getOthersPersonalBoards().get(player).getFaithTrack().isFirstFavorTile()){
            firstPopeTile.setImage(new Image("/punchboard/tile_2_front.png"));
        }
        else
            firstPopeTile.setImage(new Image("/punchboard/tile_2_back.png"));

        if (modelView.getOthersPersonalBoards().get(player).getFaithTrack().isSecondFavorTile()){
            secondPopeTile.setImage(new Image("/punchboard/tile_3_front.png"));
        }
        else
            secondPopeTile.setImage(new Image("/punchboard/tile_3_back.png"));

        if (modelView.getOthersPersonalBoards().get(player).getFaithTrack().isThirdFavorTile()){
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

        imageViews.get(modelView.getOthersPersonalBoards().get(player).getFaithTrack().getFaithMarkerPosition()).setImage(new Image("/punchboard/croce.png"));



    }

    public void setSlotImages(){
        if (modelView.getOthersPersonalBoards().get(player).getSlots()[0].getShowedCard() !=null){
            slotLeft1.setImage(new Image("/front/devcard_color-"
                    + modelView.getOthersPersonalBoards().get(player).getSlots()[0].getShowedCard().getCardColor()
                    + "_level-" + modelView.getOthersPersonalBoards().get(player).getSlots()[0].getShowedCard().getLevel()
                    + "_vp-" + modelView.getOthersPersonalBoards().get(player).getSlots()[0].getShowedCard().getVictoryPointsDevCard() + ".png"));
        }

        if (modelView.getOthersPersonalBoards().get(player).getSlots()[1].getShowedCard() !=null){
            slotCenter1.setImage(new Image("/front/devcard_color-"
                    + modelView.getOthersPersonalBoards().get(player).getSlots()[1].getShowedCard().getCardColor()
                    + "_level-" + modelView.getOthersPersonalBoards().get(player).getSlots()[1].getShowedCard().getLevel()
                    + "_vp-" + modelView.getOthersPersonalBoards().get(player).getSlots()[1].getShowedCard().getVictoryPointsDevCard() + ".png"));
        }

        if (modelView.getOthersPersonalBoards().get(player).getSlots()[2].getShowedCard() !=null){
            slotRight1.setImage(new Image("/front/devcard_color-"
                    + modelView.getOthersPersonalBoards().get(player).getSlots()[2].getShowedCard().getCardColor()
                    + "_level-" + modelView.getOthersPersonalBoards().get(player).getSlots()[2].getShowedCard().getLevel()
                    + "_vp-" + modelView.getOthersPersonalBoards().get(player).getSlots()[2].getShowedCard().getVictoryPointsDevCard() + ".png"));
        }

    }


    public void setLeaderImages(){
        if (modelView.getOthersPersonalBoards().get(player).getActiveLeaderCards().size()==0){
            leaderLeft.setImage(new Image("/back/leader-back.png"));
            leaderRight.setImage(new Image("/back/leader-back.png"));
        }

        else if (modelView.getOthersPersonalBoards().get(player).getActiveLeaderCards().size()==1){
            leaderLeft.setImage(new Image("/front/leader_"+modelView.getActiveLeaderCards().get(0).getLeaderEffect().toString()+".png"));
            leaderRight.setImage(new Image("/back/leader-back.png"));
        }

        else {
            leaderLeft.setImage(new Image("/front/leader_"+modelView.getOthersPersonalBoards().get(player).getActiveLeaderCards().get(0).getLeaderEffect().toString()+".png"));
            leaderRight.setImage(new Image("/front/leader_"+modelView.getOthersPersonalBoards().get(player).getActiveLeaderCards().get(1).getLeaderEffect().toString()+".png"));
        }
    }


    public void setImagesStrongBox(){
        coinCounter.setText(""+modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getStrongbox()[0].getQnt());
        servantCounter.setText(""+modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getStrongbox()[1].getQnt());
        shieldCounter.setText(""+modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getStrongbox()[2].getQnt());
        stoneCounter.setText(""+modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getStrongbox()[3].getQnt());
    }

    private void setExtraDepotImages() {
        if (NResourceType != ResourceType.NULLRESOURCE){ //significa che è attiva una leader card extra production
            if (modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getExtraFloors().get(0).getQnt()==1){
                NWResource.setImage(new Image("/punchboard/resources/"+NResourceType.getResourceName().toUpperCase()+".png"));
            }
            else if (modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getExtraFloors().get(0).getQnt()==2) {
                NWResource.setImage(new Image("/punchboard/resources/"+NResourceType.getResourceName().toUpperCase()+".png"));
                NEResource.setImage(new Image("/punchboard/resources/"+NResourceType.getResourceName().toUpperCase()+".png"));
            }
        }

        if (SResourceType != ResourceType.NULLRESOURCE){ //significa che è attiva una leader card extra production
            if (modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getExtraFloors().get(1).getQnt()==1) {
                SWResource.setImage(new Image("/punchboard/resources/"+SResourceType.getResourceName().toUpperCase()+".png"));
            }
            else if (modelView.getOthersPersonalBoards().get(player).getSerializableWarehouse().getExtraFloors().get(1).getQnt()==2) {
                SWResource.setImage(new Image("/punchboard/resources/"+SResourceType.getResourceName().toUpperCase()+".png"));
                SEResource.setImage(new Image("/punchboard/resources/"+SResourceType.getResourceName().toUpperCase()+".png"));
            }
        }
    }

    public void showPopUp(){
        stage.show();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }
}
