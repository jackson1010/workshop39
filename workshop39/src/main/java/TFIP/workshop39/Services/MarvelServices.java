package TFIP.workshop39.Services;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import TFIP.workshop39.Model.Character;
import TFIP.workshop39.Model.CharacterList;
import TFIP.workshop39.Model.Comment;
import TFIP.workshop39.Repo.MarvelRepo;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class MarvelServices {

    @Autowired
    private MarvelRepo marvelRepo;

    @Value("${TFIP.MARVEL.PKEY}")
    private String marvelPKEY;

    @Value("${TFIP.MARVEL.SKEY}")
    private String marvelSKEY;

    private final String baseURL = "http://gateway.marvel.com/v1/public/characters";    

    public Optional<CharacterList> getCharacterList(String name, Integer limit, Integer offset) throws IOException {

        long timestamp = System.currentTimeMillis();
        String ts = String.valueOf(timestamp);
        // System.out.println("PKEY----" + marvelPKEY);
        // System.out.println("SKEY----" + marvelSKEY);
        String hash = generateMD5Hash(Long.parseLong(ts), marvelSKEY, marvelPKEY);

        String url = String.format("%s?ts=%s&apikey=%s&hash=%s&nameStartsWith=%s&limit=%s&offset=%s",
                baseURL, ts, marvelPKEY, hash, name, limit, offset);

        // System.out.println("-------------------" + url);

        RestTemplate template = new RestTemplate();
        RequestEntity reqE = RequestEntity.get(url).build();

        ResponseEntity<String> response = template.exchange(reqE, String.class);
        CharacterList characterList = CharacterList.createFromJson(response.getBody());

        if (characterList != null) {
            marvelRepo.saveDetails(characterList);
            return Optional.of(characterList);
        }
        return Optional.empty();

    }

    public Optional<Character> getCharacterById(String characterId) throws JsonMappingException, JsonProcessingException {
        return marvelRepo.getCharacterById(characterId);
    }

    public Optional<CharacterList> getCharacterByIdFromAPI(String characterId) throws IOException {
        long timestamp = System.currentTimeMillis();
        String ts = String.valueOf(timestamp);
        // System.out.println("PKEY----" + marvelPKEY);
        // System.out.println("SKEY----" + marvelSKEY);
        String hash = generateMD5Hash(Long.parseLong(ts), marvelSKEY, marvelPKEY);

        String url = String.format("%s/%s?ts=%s&apikey=%s&hash=%s",
                baseURL, characterId, ts, marvelPKEY, hash);

        System.out.println("-------------------" + url);

        RestTemplate template = new RestTemplate();
        RequestEntity reqE = RequestEntity.get(url).build();

        ResponseEntity<String> response = template.exchange(reqE, String.class);
               
        CharacterList characterList = CharacterList.createFromJson(response.getBody());

        if (characterList != null) {
            marvelRepo.saveDetails(characterList);
            return Optional.of(characterList);
        }
        return Optional.empty();

    }

    public String generateMD5Hash(long ts, String privateKey, String publicKey) {
        try {
            String input = ts + privateKey + publicKey;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hash = number.toString(16);
            while (hash.length() < 32) {
                hash = "0" + hash;
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Comment> getCommentByID(String characterId) {
        return marvelRepo.getCommentByID(characterId);
    }

    public boolean postCommentById(String characterId, String comment) {
        return marvelRepo.postCommentById(characterId, comment);
    }

   



}
