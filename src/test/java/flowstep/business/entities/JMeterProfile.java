package flowstep.business.entities;

import org.json.simple.JSONObject;
import flowstep.utils.JsonDataReader;
import flowstep.utils.Parser;

public class JMeterProfile {

    private   JSONObject jsonDataReader = null;
    public JMeterProfile(int index){
        jsonDataReader = JsonDataReader.getJmeterByJSONObject(index);
    }

    public static JMeterProfile page(int index){
        return  new JMeterProfile(index);
    }

    public  String getFolderContainerName() {
        return Parser.asString(jsonDataReader.get("folderContainerName"));
    }

    public  String getTestPlanName() {
        return Parser.asString(jsonDataReader.get("testPlanName"));
    }

    public  String getMethod() {
        return Parser.asString(jsonDataReader.get("method"));
    }

    public  String getFileName() {
        return Parser.asString(jsonDataReader.get("fileName"));
    }

    public String getUrl() {
        return  Parser.asString(jsonDataReader.get("url"));
    }

    public long  getPort() {
        return (long) jsonDataReader.get("port");
    }

    public  String getPath() {
        return (String) jsonDataReader.get("path");
    }

    public  int getLoops() {
        return  Parser.asInt(jsonDataReader.get("loops"));
    }

    public  int getNumThreads() {
        return  Parser.asInt(jsonDataReader.get("numThreads"));
    }

    public  int getRamUps() {
        return  Parser.asInt(jsonDataReader.get("ramUps"));
    }

    public  String getNameOfThreadGroup() {
        return Parser.asString(jsonDataReader.get("nameOfThreadGroup"));
    }

    public  long getDuration() {
        return  Parser.asLong(jsonDataReader.get("duration"));
    }

}