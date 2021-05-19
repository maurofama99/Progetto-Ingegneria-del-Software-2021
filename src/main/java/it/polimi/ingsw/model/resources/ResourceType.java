package it.polimi.ingsw.model.resources;

import com.google.gson.annotations.SerializedName;

/**
 * This enum contains all types of Resources that a player can have,
 * it contains also Faith Point and white Resource that is used as a null Resource.
 */

public enum ResourceType {
    @SerializedName("0")
    SHIELD,
    @SerializedName("1")
    SERVANT,
    @SerializedName("2")
    COIN,
    @SerializedName("3")
    STONE,
    @SerializedName("4")
    WHITERESOURCE,
    @SerializedName("5")
    FAITHPOINT,
    @SerializedName("6")
    NULLRESOURCE;

}
