package it.polimi.ingsw.model.devcard;

import com.google.gson.annotations.SerializedName;


/**
 * Color of the development cards, made serialized for json.
*/
 public enum Color {
    @SerializedName("0")
    GREEN,
    @SerializedName("1")
    BLUE,
    @SerializedName("2")
    YELLOW,
    @SerializedName("3")
    PURPLE
}
