package TFIP.workshop39.Model;

import java.util.List;

import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;

public class Character {
    private Integer id;
    private String name;
    private Thumbnail thumbnail;
    private List<Comment> comment;

    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Thumbnail getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
    public List<Comment> getComment() {
        return comment;
    }
    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    
    public static Character createFromJson(JsonObject jso) {
        Character c = new Character();
        c.setId(jso.getJsonNumber("id").intValue());
        c.setName(jso.getString("name"));
        JsonObject thumbnail= jso.getJsonObject("thumbnail");
        c.setThumbnail(Thumbnail.createFromJson(thumbnail));
        return c;
    }


    

    
}
