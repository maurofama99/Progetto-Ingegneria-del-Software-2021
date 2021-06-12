package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.ExtraDepot;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
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

import java.util.ArrayList;

public class PersonalBoardSceneController extends ClientObservable implements GenericSceneController {

    //TODO: assigning inkwell to first player
    private ModelView modelView;
    private ResourceType NResourceType = ResourceType.NULLRESOURCE;// upper card resource type if extra depot
    private ResourceType SResourceType = ResourceType.NULLRESOURCE;// lower card resource type if extra depot
    private String firstPlayerNick, secondPlayerNick, thirdPlayerNick;
    private boolean firstPlayer = false;

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
    private ImageView firstPopeTile;
    @FXML
    private ImageView secondPopeTile;
    @FXML
    private ImageView thirdPopeTile;
    //End of FaithTrack
    @FXML
    private Button firstPlayerBtn, secondPlayerBtn, thirdPlayerBtn;
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

    public PersonalBoardSceneController(ModelView modelView, boolean firstPlayer) {
        this.modelView = modelView;
        this.firstPlayer = firstPlayer;

        for (LeaderCard leaderCard : modelView.getActiveLeaderCards()){
            if (leaderCard.getLeaderEffect().getEffectType() == EffectType.EXTRADEPOT){
                if (modelView.getActiveLeaderCards().indexOf(leaderCard) == 0){
                    NResourceType = (ResourceType) leaderCard.getLeaderEffect().getObject();
                } else {
                    SResourceType = (ResourceType) leaderCard.getLeaderEffect().getObject();
                }
            }
        }

    }

    public void initialize(){

        setOtherPlayersButtons();
        setDepotImage();
        setFaithTrackImages();
        setSlotImages();
        setImagesStrongBox();
        setLeaderImages();
        setExtraDepotImages();
        setInkwell();
    }

    private void setInkwell(){
        if (firstPlayer) inkwell.setImage(new Image("/punchboard/inkwell.png"));
    }

    private void setExtraDepotImages() {
        if (NResourceType != ResourceType.NULLRESOURCE){ //significa che è attiva una leader card extra production
            if (modelView.getWarehouse().getExtraFloors().get(0).getQnt()==1){
                NWResource.setImage(new Image("/punchboard/resources/"+NResourceType.getResourceName().toUpperCase()+".png"));
            }
            else if (modelView.getWarehouse().getExtraFloors().get(0).getQnt()==2) {
                NWResource.setImage(new Image("/punchboard/resources/"+NResourceType.getResourceName().toUpperCase()+".png"));
                NEResource.setImage(new Image("/punchboard/resources/"+NResourceType.getResourceName().toUpperCase()+".png"));
            }
        }

        if (SResourceType != ResourceType.NULLRESOURCE){ //significa che è attiva una leader card extra production
            if (modelView.getWarehouse().getExtraFloors().get(1).getQnt()==1) {
                SWResource.setImage(new Image("/punchboard/resources/"+SResourceType.getResourceName().toUpperCase()+".png"));
            }
            else if (modelView.getWarehouse().getExtraFloors().get(1).getQnt()==2) {
                SWResource.setImage(new Image("/punchboard/resources/"+SResourceType.getResourceName().toUpperCase()+".png"));
                SEResource.setImage(new Image("/punchboard/resources/"+SResourceType.getResourceName().toUpperCase()+".png"));
            }
        }
    }

    private void whenThirdPlayerBtnClicked(MouseEvent event) {
        Platform.runLater(()->{
            ShowOthersPopupController sosc = new ShowOthersPopupController(thirdPlayerNick, modelView);
            sosc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sosc, "show_others.fxml");
        });
    }

    private void whenSecondPlayerBtnClicked(MouseEvent event) {
        Platform.runLater(()->{
            ShowOthersPopupController sosc = new ShowOthersPopupController(secondPlayerNick, modelView);
            sosc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sosc, "show_others.fxml");
        });
    }

    private void whenFirstPlayerBtnClicked(MouseEvent event) {
        Platform.runLater(()->{
            ShowOthersPopupController sosc = new ShowOthersPopupController(firstPlayerNick, modelView);
            sosc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sosc, "show_others.fxml");
        });
    }

    static void setResourceImage(String resource, ImageView spot) {
        spot.setImage(new Image("/punchboard/resources/"+resource+".png"));
    }

    private void setOtherPlayersButtons(){
        firstPlayerBtn.setDisable(true);
        secondPlayerBtn.setDisable(true);
        thirdPlayerBtn.setDisable(true);

        for(String player : modelView.getOthersPersonalBoards().keySet()){
            if (firstPlayerBtn.isDisabled()){
                firstPlayerBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: whenFirstPlayerBtnClicked);
                firstPlayerBtn.setText(player);
                firstPlayerNick = player;
                firstPlayerBtn.setDisable(false);
            }
            else if (secondPlayerBtn.isDisabled()){
                secondPlayerBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: whenSecondPlayerBtnClicked);
                secondPlayerBtn.setText(player);
                secondPlayerNick = player;
                secondPlayerBtn.setDisable(false);
            }
            else if (thirdPlayerBtn.isDisabled()){
                thirdPlayerBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: whenThirdPlayerBtnClicked);
                thirdPlayerNick = player;
                thirdPlayerBtn.setText(player);
                thirdPlayerBtn.setDisable(false);
            }
        }
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

        if(modelView.getFaithTrack().getBlackCrossPosition()==modelView.getFaithTrack().getFaithMarkerPosition()) {

            imageViews.get(modelView.getFaithTrack().getFaithMarkerPosition()).setImage(new Image("/punchboard/double_cross.png"));
        }
        else if(modelView.getFaithTrack().getBlackCrossPosition()>=0)
        {
            imageViews.get(modelView.getFaithTrack().getBlackCrossPosition()).setImage(new Image("/punchboard/blackcross.png"));
        }

    }

    public void setSlotImages(){
        if (modelView.getSlots()[0].getShowedCard() !=null){
            if(modelView.getSlots()[0].getCards().size()==1) {
                slotLeft1.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[0].getShowedCard().getCardColor()
                        + "_level-" + modelView.getSlots()[0].getShowedCard().getLevel()
                        + "_vp-" + modelView.getSlots()[0].getShowedCard().getVictoryPointsDevCard() + ".png"));
            }
            else if(modelView.getSlots()[0].getCards().size()==2){
                slotLeft1.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[0].getCards().get(0).getCardColor()
                        + "_level-" + modelView.getSlots()[0].getCards().get(0).getLevel()
                        + "_vp-" + modelView.getSlots()[0].getCards().get(0).getVictoryPointsDevCard() + ".png"));
                slotLeft2.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[0].getShowedCard().getCardColor()
                        + "_level-" + modelView.getSlots()[0].getShowedCard().getLevel()
                        + "_vp-" + modelView.getSlots()[0].getShowedCard().getVictoryPointsDevCard() + ".png"));
            }
            else if(modelView.getSlots()[0].getCards().size()==3){
                slotLeft1.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[0].getCards().get(0).getCardColor()
                        + "_level-" + modelView.getSlots()[0].getCards().get(0).getLevel()
                        + "_vp-" + modelView.getSlots()[0].getCards().get(0).getVictoryPointsDevCard() + ".png"));
                slotLeft2.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[0].getCards().get(1).getCardColor()
                        + "_level-" + modelView.getSlots()[0].getCards().get(1).getLevel()
                        + "_vp-" + modelView.getSlots()[0].getCards().get(1).getVictoryPointsDevCard() + ".png"));
                slotLeft3.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[0].getShowedCard().getCardColor()
                        + "_level-" + modelView.getSlots()[0].getShowedCard().getLevel()
                        + "_vp-" + modelView.getSlots()[0].getShowedCard().getVictoryPointsDevCard() + ".png"));
            }
        }

        if (modelView.getSlots()[1].getShowedCard() !=null){
            if(modelView.getSlots()[1].getCards().size()==1) {
                slotCenter1.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[1].getShowedCard().getCardColor()
                        + "_level-" + modelView.getSlots()[1].getShowedCard().getLevel()
                        + "_vp-" + modelView.getSlots()[1].getShowedCard().getVictoryPointsDevCard() + ".png"));
            }
            else if(modelView.getSlots()[1].getCards().size()==2) {
                slotCenter1.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[1].getCards().get(0).getCardColor()
                        + "_level-" + modelView.getSlots()[1].getCards().get(0).getLevel()
                        + "_vp-" + modelView.getSlots()[1].getCards().get(0).getVictoryPointsDevCard() + ".png"));
                slotCenter2.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[1].getShowedCard().getCardColor()
                        + "_level-" + modelView.getSlots()[1].getShowedCard().getLevel()
                        + "_vp-" + modelView.getSlots()[1].getShowedCard().getVictoryPointsDevCard() + ".png"));
            }
            else if(modelView.getSlots()[1].getCards().size()==3) {
                slotCenter1.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[1].getCards().get(0).getCardColor()
                        + "_level-" + modelView.getSlots()[1].getCards().get(0).getLevel()
                        + "_vp-" + modelView.getSlots()[1].getCards().get(0).getVictoryPointsDevCard() + ".png"));
                slotCenter2.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[1].getCards().get(1).getCardColor()
                        + "_level-" + modelView.getSlots()[1].getCards().get(1).getLevel()
                        + "_vp-" + modelView.getSlots()[1].getCards().get(1).getVictoryPointsDevCard() + ".png"));
                slotCenter3.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[1].getShowedCard().getCardColor()
                        + "_level-" + modelView.getSlots()[1].getShowedCard().getLevel()
                        + "_vp-" + modelView.getSlots()[1].getShowedCard().getVictoryPointsDevCard() + ".png"));
            }
        }

        if (modelView.getSlots()[2].getShowedCard() !=null){
            if(modelView.getSlots()[2].getCards().size()==1) {
                slotRight1.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[2].getShowedCard().getCardColor()
                        + "_level-" + modelView.getSlots()[2].getShowedCard().getLevel()
                        + "_vp-" + modelView.getSlots()[2].getShowedCard().getVictoryPointsDevCard() + ".png"));
            }
            else if(modelView.getSlots()[2].getCards().size()==2) {
                slotRight1.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[2].getCards().get(0).getCardColor()
                        + "_level-" + modelView.getSlots()[2].getCards().get(0).getLevel()
                        + "_vp-" + modelView.getSlots()[2].getCards().get(0).getVictoryPointsDevCard() + ".png"));
                slotRight2.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[2].getShowedCard().getCardColor()
                        + "_level-" + modelView.getSlots()[2].getShowedCard().getLevel()
                        + "_vp-" + modelView.getSlots()[2].getShowedCard().getVictoryPointsDevCard() + ".png"));
            }
            else if(modelView.getSlots()[2].getCards().size()==3) {
                slotRight1.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[2].getCards().get(0).getCardColor()
                        + "_level-" + modelView.getSlots()[2].getCards().get(0).getLevel()
                        + "_vp-" + modelView.getSlots()[2].getCards().get(0).getVictoryPointsDevCard() + ".png"));
                slotRight2.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[2].getCards().get(1).getCardColor()
                        + "_level-" + modelView.getSlots()[2].getCards().get(1).getLevel()
                        + "_vp-" + modelView.getSlots()[2].getCards().get(1).getVictoryPointsDevCard() + ".png"));
                slotRight3.setImage(new Image("/front/devcard_color-"
                        + modelView.getSlots()[2].getShowedCard().getCardColor()
                        + "_level-" + modelView.getSlots()[2].getShowedCard().getLevel()
                        + "_vp-" + modelView.getSlots()[2].getShowedCard().getVictoryPointsDevCard() + ".png"));
            }
        }


    }

    public void setLeaderImages(){
        if (modelView.getActiveLeaderCards().size()==0){
            leaderLeft.setImage(new Image("/back/leader-back.png"));
            leaderRight.setImage(new Image("/back/leader-back.png"));
        }

        else if (modelView.getActiveLeaderCards().size()==1){
            leaderLeft.setImage(new Image("/front/leader_"+modelView.getActiveLeaderCards().get(0).getLeaderEffect().toString()+".png"));
            leaderRight.setImage(new Image("/back/leader-back.png"));
        }

        else {
            leaderLeft.setImage(new Image("/front/leader_"+modelView.getActiveLeaderCards().get(0).getLeaderEffect().toString()+".png"));
            leaderRight.setImage(new Image("/front/leader_"+modelView.getActiveLeaderCards().get(1).getLeaderEffect().toString()+".png"));
        }
    }


    public void setImagesStrongBox(){
        coinCounter.setText(""+modelView.getWarehouse().getStrongbox()[0].getQnt());
        servantCounter.setText(""+modelView.getWarehouse().getStrongbox()[1].getQnt());
        shieldCounter.setText(""+modelView.getWarehouse().getStrongbox()[2].getQnt());
        stoneCounter.setText(""+modelView.getWarehouse().getStrongbox()[3].getQnt());
    }

}
