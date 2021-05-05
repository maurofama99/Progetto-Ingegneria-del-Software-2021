package it.polimi.ingsw.model.player.leadercards;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class LeaderEffectJsonDeserializer implements JsonDeserializer<LeaderEffect> {

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