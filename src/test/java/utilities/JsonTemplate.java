package utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class JsonTemplate {
    String team;
    List<String> photos;

    public JsonTemplate(String team, List<String> photos) {
        this.team = team;
        this.photos = photos;
    }

    public String getJsonString(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonData = gson.toJson(this);
        return jsonData;
    }
}
