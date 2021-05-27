package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observerPattern.*;
import it.polimi.ingsw.view.gui.scenes.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class SceneController extends ClientObservable {

    private static Scene onGoingScene;
    private static GenericSceneController activeController;

    public static Scene getOnGoingScene() {
        return onGoingScene;
    }

    public static GenericSceneController getActiveController() {
        return activeController;
    }

    public static <T> T changeRootPane(List<ClientObserver> clientObservers, Scene scene, String fxml){
        T controller = null;

        try{
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController();
            ((ClientObservable) controller).addAllClientObservers(clientObservers);

            activeController = (GenericSceneController) controller;
            onGoingScene = scene;
            onGoingScene.setRoot(root);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return controller;
    }


    public static <T> T changeRootPane(List<ClientObserver> clientObservers, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        return changeRootPane(clientObservers, scene, fxml);
    }

    public static <T> T changeRootPane(List<ClientObserver> clientObservers, String fxml) {
        return changeRootPane(clientObservers, onGoingScene, fxml);
    }

    public static void changeRootPane(GenericSceneController controller, Scene scene, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));

            // Setting the controller BEFORE the load() method.
            loader.setController(controller);
            activeController = controller;
            Parent root = loader.load();

            onGoingScene = scene;
            onGoingScene.setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void changeRootPane(GenericSceneController controller, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        changeRootPane(controller, scene, fxml);
    }

    public static void changeRootPane(GenericSceneController controller, String fxml) {
        changeRootPane(controller, onGoingScene, fxml);
    }

    public static <T> T showPopup(List<ClientObserver> clientObservers, String title, String message){

        T controller;
        FXMLLoader popLoader = new FXMLLoader(SceneController.class.getResource("/fxml/popup_scene.fxml"));
        Parent parent;

        try {
            parent = popLoader.load();
            controller = popLoader.getController();
            ((ClientObservable) controller).addAllClientObservers(clientObservers);
        } catch (IOException exception){
            System.err.println(exception);
            return null;
        }

        PopupSceneController psc =popLoader.getController();
        Scene popupScene = new Scene(parent);
        psc.setScene(popupScene);
        psc.setTitleLabel(title);
        psc.setMessageOfLabel(message);
        psc.showPopUp();
        return controller;

    }

    public static void showPopup(List<ClientObserver> clientObservers, String message){
        showPopup(clientObservers, "Info", message);
    }

    public static <T> T showDepositPopup(List<ClientObserver> clientObservers, String resource){
        T controller;
        FXMLLoader depoPopLoader = new FXMLLoader(SceneController.class.getResource("/fxml/deposit_popup.fxml"));
        Parent parent;

        try {
            parent = depoPopLoader.load();
            controller = depoPopLoader.getController();
            ((ClientObservable) controller).addAllClientObservers(clientObservers);
        } catch (IOException exception){
            System.err.println(exception);
            return null;
        }

        DepositPopupSceneController dpsc =depoPopLoader.getController();
        Scene popupScene = new Scene(parent);
        dpsc.setScene(popupScene);
        dpsc.setResourceLbl(resource);
        dpsc.setResourceImg(resource);
        dpsc.showPopUp();
        return controller;
    }

    public static <T> T showActionPopup(List<ClientObserver> clientObservers, String message){
        T controller;
        FXMLLoader actPopLoader = new FXMLLoader(SceneController.class.getResource("/fxml/action_popup.fxml"));
        Parent parent;

        try {
            parent = actPopLoader.load();
            controller = actPopLoader.getController();
            ((ClientObservable) controller).addAllClientObservers(clientObservers);
        } catch (IOException exception){
            System.err.println(exception);
            return null;
        }

        ActionPopupController apsc =actPopLoader.getController();
        Scene popupScene = new Scene(parent);
        apsc.setScene(popupScene);
        apsc.showPopUp();
        return controller;
    }

    public static <T> T LeaderStartPopup(List<ClientObserver> clientObservers, String message){
        T controller;
        FXMLLoader leaderPopLoader = new FXMLLoader(SceneController.class.getResource("/fxml/leader_popup.fxml"));
        Parent parent;

        try {
            parent = leaderPopLoader.load();
            controller = leaderPopLoader.getController();
            ((ClientObservable) controller).addAllClientObservers(clientObservers);
        } catch (IOException exception){
            System.err.println(exception);
            return null;
        }

        LeaderStartSceneController lssc =leaderPopLoader.getController();
        Scene popupScene = new Scene(parent);
        lssc.setScene(popupScene);
        lssc.showPopUp();
        return controller;
    }

    public static <T> T showSingleplayerPopup(List<ClientObserver> clientObservers, String message){
        T controller;
        FXMLLoader singlePopLoader = new FXMLLoader(SceneController.class.getResource("/fxml/singleplayer_popup.fxml"));
        Parent parent;

        try {
            parent = singlePopLoader.load();
            controller = singlePopLoader.getController();
            ((ClientObservable) controller).addAllClientObservers(clientObservers);
        } catch (IOException exception){
            System.err.println(exception);
            return null;
        }

        SingleplayerPopupSceneController spsc =singlePopLoader.getController();
        Scene popupScene = new Scene(parent);
        spsc.setScene(popupScene);
        spsc.setMessageOfLabel(message);
        spsc.showPopUp();
        return controller;
    }

}
