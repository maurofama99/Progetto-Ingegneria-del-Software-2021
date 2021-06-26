package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observerPattern.*;
import it.polimi.ingsw.view.gui.scenes.*;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

/**
 * Core of the switching scene action. It's all here.
 */
public class SceneController extends ClientObservable {

    private static Scene onGoingScene;
    private static GenericSceneController activeController;

    public static Scene getOnGoingScene() {
        return onGoingScene;
    }

    public static GenericSceneController getActiveController() {
        return activeController;
    }

    /**
     * This method changes the scene and puts the controller specified in the "FXML" file as the active Controller
     * @param clientObservers all the observers of the clients
     * @param scene the scene ( can be the on going one)
     * @param fxml the fxml that will be displayed
     * @param <T> it's the controller of the scene to be set
     * @return the controller of the scene
     */
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
            System.out.println("Error in changeRootPane(observers) "+ e.getMessage());
        }
        return controller;
    }

    /**
     * Same as above, but the one parameter is an event, so we can overload the method and use it only when a certain
     * action is performed. Since the scene is not an argument, we take the one from where the event started
     * @param clientObservers observers of the clients
     * @param event the event we want to cause the switch scene from
     * @param fxml the fxml to show
     * @param <T> a controller
     * @return the overloaded method with the scene set
     */
    public static <T> T changeRootPane(List<ClientObserver> clientObservers, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        return changeRootPane(clientObservers, scene, fxml);
    }

    /**
     * Overloaded method, we don't specify the scene nor an event, so it simply shows a new fxml taking the ongoingScene as a
     * parameter
     * @param clientObservers the observers of the client
     * @param fxml the fxml to show
     * @param <T> a controller
     * @return the overloaded method, with the ongoingScene as a parameter for the Scene.
     */
    public static <T> T changeRootPane(List<ClientObserver> clientObservers, String fxml) {
        return changeRootPane(clientObservers, onGoingScene, fxml);
    }

    /**
     * Since using the controller and assigning it from the FXML file gives lots of problems , because we couldn't
     * change the parameters, we use this method for changing the scene AND assigning the controller to that scene.
     * Most of the times is the same controller we used to assign, but we create it before hand and change some parameters
     * before making the activeController.
     * Since we don't have the observers as parameters, we add all of them to the controller BEFORE assigning it
     * @param controller the controller we want on the scene
     * @param scene the current scene
     * @param fxml the fxml to show
     */
    public static void changeRootPane(GenericSceneController controller, Scene scene, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));

            loader.setController(controller);
            activeController = controller;
            Parent root = loader.load();

            onGoingScene = scene;
            onGoingScene.setRoot(root);
        } catch (IOException e) {
            System.out.println("Error in changeRootPane(controller) "+ e.getMessage());
        }
    }

    /**
     * If we don't want to specify the Scene, we overload the method and it calls the first one with the
     * onGoingScene as a parameter
     * @param controller the controller we want on the scene
     * @param fxml the fxml to show
     */
    public static void changeRootPane(GenericSceneController controller, String fxml) {
        changeRootPane(controller, onGoingScene, fxml);
    }

    /**
     * We used to have lots of methods, that all called a specific popup. Now we have a single method that makes them
     * appear, sets the controller and we only have to pass the controller created and the fxml of the popup as arguments.
     * @param controller the controller of the popup
     * @param fxml the fxml of the popup that will be shown
     */
    public static void showPopup(GenericPopupController controller, String fxml){

        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));

            loader.setController(controller);
            Parent parent = loader.load();
            Scene popupScene = new Scene(parent);
            controller.setScene(popupScene);
            controller.showPopUp();

        } catch (IOException e) {
            System.out.println("Error in showPopup " + e.getMessage());
        }

    }


}
