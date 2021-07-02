package it.polimi.ingsw.view.gui.scenes;

import javafx.scene.Scene;

/**
 * Interface for the popups. Every popup needs to implement this.
 */
public interface GenericPopupController {
    /**
     * These two methods are called to manage the popup scene
     */
    void showPopUp();

    void setScene(Scene scene);

}
