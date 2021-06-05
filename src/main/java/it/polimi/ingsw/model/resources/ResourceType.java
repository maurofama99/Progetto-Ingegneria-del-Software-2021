package it.polimi.ingsw.model.resources;

import com.google.gson.annotations.SerializedName;

/**
 * This enum contains all types of Resources that a player can have,
 * it contains also Faith Point and white Resource that is used as a null Resource.
 */

public enum ResourceType {
    @SerializedName("0")
    SHIELD("shield"),
    @SerializedName("1")
    SERVANT("servant"),
    @SerializedName("2")
    COIN("coin"),
    @SerializedName("3")
    STONE("stone"),
    @SerializedName("4")
    WHITERESOURCE("white resource"),
    @SerializedName("5")
    FAITHPOINT("faith point"),
    @SerializedName("6")
    NULLRESOURCE("null resource");

    private final String resourceName;

    ResourceType(String value) {
       resourceName = value;
    }

    public String getResourceName() {
        return resourceName;
    }

}
