package TFIP.workshop39.Model;

import jakarta.json.JsonObject;

public class Thumbnail {
    private String path;
    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public static Thumbnail createFromJson(JsonObject jso) {
        Thumbnail t = new Thumbnail();
        t.setExtension(jso.getString("path"));
        t.setPath(jso.getString("extension"));
        return t;
    }

}
