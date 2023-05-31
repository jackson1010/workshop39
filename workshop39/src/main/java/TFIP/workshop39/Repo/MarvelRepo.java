package TFIP.workshop39.Repo;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import TFIP.workshop39.Model.CharacterList;
import TFIP.workshop39.Model.Comment;
import TFIP.workshop39.Model.Character;

@Repository
public class MarvelRepo {

    @Autowired
    RedisTemplate<String, String> templates;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveDetails(CharacterList characterList) throws JsonProcessingException {

        for (Character c : characterList.getCharacters()) {
            ObjectMapper ob = new ObjectMapper();
            String jsonString = ob.writeValueAsString(c);
            templates.opsForValue().set(c.getId().toString(), jsonString.toString(), Duration.ofHours(1));
        }
    }

    public Optional<Character> getCharacterById(String characterId)
            throws JsonMappingException, JsonProcessingException {

        String result = templates.opsForValue().get(characterId);

        if (result != null) {
            ObjectMapper mapper = new ObjectMapper();
            Character character = mapper.readValue(result, Character.class);
            return Optional.of(character);
        }
        return Optional.empty();

    }

    public List<Comment> getCommentByID(String characterId) {

        Query query = new Query();
        Criteria criteria = Criteria.where("characterId").is(characterId);
        query.addCriteria(criteria);
        System.out.println("searching in mongo");
        return mongoTemplate.find(query.limit(10), Comment.class, "comments");
    }

    public boolean postCommentById(String characterId, String comment) {

        Document doc = new Document()
        .append("characterId", characterId)
        .append("comment", comment)
        .append("timeStamp", (new Date()).toString());

        Document result= mongoTemplate.insert(doc, "comments");
        System.out.println(result);

        return result!=null;
    }
}
