package utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonTemplate {
    String team;
//    Map<String, Integer> photos ;
//    Map<String, Integer> photos = new HashMap<String, Integer>();
     List<Photos> photos = new ArrayList<>();

    public JsonTemplate(String team, Map<String, Integer> photos) {
        this.team = team;
//        this.photos = photos;
        for (Map.Entry<String,Integer> entry : photos.entrySet()){
            this.photos.add(new Photos(entry.getKey(),entry.getValue()));
        }
    }

    public String getJsonString(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonData = gson.toJson(this);
        return jsonData;
    }
}
