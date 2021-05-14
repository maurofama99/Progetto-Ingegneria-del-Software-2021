package it.polimi.ingsw.model.player.leadercards;

import com.google.gson.annotations.SerializedName;

public enum EffectType {
    @SerializedName("addproduction")
    ADDPRODUCTION,
    @SerializedName("discount")
    DISCOUNT,
    @SerializedName("extradepot")
    EXTRADEPOT,
    @SerializedName("swapwhite")
    SWAPWHITE;

}
