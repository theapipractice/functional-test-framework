package rga.utils;

import common.utils.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static String asString(Object object){
        return object == null ? "" : String.valueOf(object);
    }

    public static JSONObject asJson(String responseBody){
        try {
            JSONParser parser = new JSONParser();
            return  (JSONObject) parser.parse(responseBody);
        }catch (Exception ex){
            Log.error(ex.getMessage());
        }
        return null;
    }

    public static long asLong(Object object){
        return object == null ? 0 : Long.parseLong(String.valueOf(object));
    }

    public static int asInt(Object object){
        return object == null ? 0 : Integer.parseInt(String.valueOf(object));
    }

    public static List<JSONObject> asListJson(String responseBody){
        JSONParser parser = new JSONParser();
        List<JSONObject> lists = new ArrayList<>();
        try {
            Object obj = parser.parse(responseBody);
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                lists.add(jsonObject);
            }
        }catch (Exception ex){
            Log.error(ex.getMessage());
        }

        return lists;
    }
}
