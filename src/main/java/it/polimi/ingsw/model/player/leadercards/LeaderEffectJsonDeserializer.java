package it.polimi.ingsw.model.player.leadercards;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;


public class LeaderEffectJsonDeserializer implements JsonDeserializer<LeaderEffect> {

    /**
     * Json deserializer created to execute custom deserialization of the leader cards Json for the leader card effect. LeaderEffect
     * abstract class attribute in LeaderCard class is instantiated with one of his child class depending on its attribute effectType.
     * @param json Leader card Json file
     * @param typeOf The type of the data structure to deserialize to
     * @param context the context of the json deserialization
     * @return deserialized leader effect
     * @throws JsonParseException if json is not in the expected format of typeofT
     */
    @Override
    public LeaderEffect deserialize(JsonElement json, Type typeOf, JsonDeserializationContext context) throws JsonParseException {
        String type = json.getAsJsonObject().get("effectType").getAsString();
        switch(type) {
            case "addproduction":
                return context.deserialize(json, AddProduction.class);
            case "discount":
                return context.deserialize(json, Discount.class);
            case "extradepot":
                return context.deserialize(json, ExtraDepot.class);
            case "swapwhite":
                return context.deserialize(json, SwapWhite.class);
            default:
                throw new IllegalArgumentException("Error in custom deserializer");
        }
    }
}
