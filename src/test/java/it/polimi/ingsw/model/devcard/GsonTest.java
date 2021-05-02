package it.polimi.ingsw.model.devcard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GsonTest {

    @Test
    public void Test1() throws FileNotFoundException {

        Gson gson = new Gson();

        try (Reader reader = new FileReader("src/main/resources/DevelopmentCards.json")) {

            Type devCardListType = new TypeToken<ArrayList<DevCard>>(){}.getType();

            ArrayList<DevCard> devCardsArray = gson.fromJson(reader, devCardListType);

            for(DevCard card : devCardsArray) {
                System.out.println(card);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
