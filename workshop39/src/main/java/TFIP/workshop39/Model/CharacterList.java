package TFIP.workshop39.Model;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class CharacterList {
    private List<Character> characters;

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public static CharacterList createFromJson(String json) throws IOException {
        CharacterList characterList = new CharacterList();
        List<Character> charList = new LinkedList<>();
        StringReader sr = new StringReader(json);
        JsonReader jr = Json.createReader(sr);
        JsonObject jso = jr.readObject();
        JsonObject data = jso.getJsonObject("data");

        JsonArray list = data.getJsonArray("results");

        for (int i = 0; i < list.size(); i++) {
            // retrieves the ith element of the JsonArray types as a JsonObject
            JsonObject x = list.getJsonObject(i);
            Character c = Character.createFromJson(x);
            charList.add(c);
        }
        characterList.setCharacters(charList);
        return characterList;
    }

}
