package TFIP.workshop39.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import TFIP.workshop39.Model.CharacterList;
import TFIP.workshop39.Model.Comment;
import TFIP.workshop39.Model.Character;
import TFIP.workshop39.Services.MarvelServices;
import jakarta.validation.Valid;

@RestController
@RequestMapping
public class MarvelController {

    @Autowired
    private MarvelServices mServices;

    @GetMapping(path = "/api/characters")
    public ResponseEntity<String> getHeroes(@RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset, @RequestParam String name) throws IOException {

        CharacterList characterList = mServices.getCharacterList(name, limit, offset).get();
        ObjectMapper ob = new ObjectMapper();
        String jsonString = ob.writeValueAsString(characterList);

        return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(jsonString);
    }

    @GetMapping(path = "/api/character/{characterId}")
    public ResponseEntity<String> getHeroById(@PathVariable String characterId) throws IOException {

        Optional<Character> c = mServices.getCharacterById(characterId);
        ObjectMapper ob = new ObjectMapper();
        String jsonString;
        Character chara = new Character();
       
        List<Comment> commentList = mServices.getCommentByID(characterId);
        for (Comment co : commentList) {
            System.out.println(co);
        }

        if (c.isPresent()) {
            System.out.println("found in redis");
            chara = c.get();
            chara.setComment(commentList);
            jsonString = ob.writeValueAsString(chara);
        } else {
            System.out.println("not found in redis");
            CharacterList clist = mServices.getCharacterByIdFromAPI(characterId).get();
            chara = clist.getCharacters().get(0);
            chara.setComment(commentList);
            jsonString = ob.writeValueAsString(chara);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(jsonString);
    }

    @PostMapping(path = "api/character/{characterId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postComment(@PathVariable String characterId, @RequestBody String comment) throws JsonMappingException, JsonProcessingException {

        ObjectMapper ob = new ObjectMapper();
        JsonNode jsonNode = ob.readTree(comment);
        String value = jsonNode.get("comment").asText();

       boolean result= mServices.postCommentById(characterId, value);
       String inserted;
       if(result == true){
        inserted=ob.writeValueAsString("Inserted");
       }else{
        inserted=ob.writeValueAsString("failed");
       }

        return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(inserted);
    }
}
