package rga.utils;

import com.google.gson.JsonObject;
import com.mongodb.util.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonDataReader {

    private static final String JMeterProfile = Common.baseFolder() + "src/test/resources/jsondata/JMeterProfile.json";

    public JsonDataReader(){

    }

    public  static JSONObject getJmeterByJSONObject(int index){
        JSONParser parser = new JSONParser();
        JSONObject value = null;
        try {

            Object obj = parser.parse(new FileReader(JMeterProfile));
            JSONArray jsonObject = (JSONArray) obj;
            JSONObject arr = (JSONObject) jsonObject.get(index);

            Object obj2 = new JSONParser().parse(arr.toJSONString());
            JSONObject jo = (JSONObject) obj2;
            value = jo;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return value;
    }

    public static List<JSONObject> getListHospitalById(String id) {
        JSONParser parser = new JSONParser();
        List<JSONObject> lists = new ArrayList<>();
        try {
            Object obj = parser.parse(new FileReader("src/test/resources/jsondata/GetStrapi.json"));
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if (jsonObject.get("id").toString().equalsIgnoreCase(id)) {
                    lists.add(jsonObject);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return lists;
    }

    public static List<JSONObject> getListHospitalByOrganization(String name) {
        JSONParser parser = new JSONParser();
        List<JSONObject> lists = new ArrayList<>();
        try {
            Object obj = parser.parse(new FileReader("src/test/resources/jsondata/GetStrapi.json"));
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if (jsonObject.get("organization").toString().equalsIgnoreCase(name)) {
                    lists.add(jsonObject);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return lists;
    }

    public static JSONObject getPost(int index) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/test/resources/jsondata/PostStrapi.json"));
            JSONArray jsonArray = (JSONArray) obj;
            return (JSONObject) jsonArray.get(index);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[]) throws Throwable {
        System.out.println(getListHospitalByOrganization("3M TEMBILAHAN, RIAU - RS").size());
    }
}