package it.polimi.ingsw.model.resources;

import com.google.gson.annotations.SerializedName;

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
    FAITHPOINT
}
