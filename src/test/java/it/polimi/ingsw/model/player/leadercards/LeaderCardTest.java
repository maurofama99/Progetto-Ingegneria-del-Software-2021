package it.polimi.ingsw.model.player.leadercards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.devcard.DevCard;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LeaderCardTest {

    @Test
    public void gsonTestLeaderCards(){

        try (Reader reader = new FileReader("src/main/resources/LeaderCards.json")) {

            Type leaderCardArrayListType = new TypeToken<ArrayList<LeaderCard>>(){}.getType();

            Gson gson = new GsonBuilder().registerTypeAdapter(LeaderEffect.class, new LeaderEffectJsonDeserializer()).create();

            ArrayList<LeaderCard> cards = gson.fromJson(reader, leaderCardArrayListType);

            System.out.println(cards);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}