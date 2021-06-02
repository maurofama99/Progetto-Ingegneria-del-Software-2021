package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ShowOthersSceneController extends ClientObservable implements GenericPopupController {

    private final String player;

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
    @FXML
    private Label nickname;
    @FXML
    private Button closeBtn;

    public ShowOthersSceneController(String nickname) {
        this.player = nickname;
    }

    public void initialize(){

    }


}
