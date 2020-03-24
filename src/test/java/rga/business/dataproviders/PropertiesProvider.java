package rga.business.dataproviders;

import com.google.gson.Gson;
import rga.business.entities.PropertyEntity;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PropertiesProvider {

    private static List<PropertyEntity> getPropertyData() {
        Gson gson = new Gson();
        BufferedReader bufferReader = null;
        try {
            bufferReader = new BufferedReader(new FileReader("src/test/resources/data/Properties.json"));
            PropertyEntity[] propertyEntities = gson.fromJson(bufferReader, PropertyEntity[].class);
            return Arrays.asList(propertyEntities);
        }catch(FileNotFoundException e) {
            throw new RuntimeException("Json file not found at path : ");
        }finally {
            try { if(bufferReader != null) bufferReader.close();}
            catch (IOException ignore) {}
        }
    }

    public static final PropertyEntity getPropertyByType(String type){
        return getPropertyData().stream().filter(x -> x.type.equalsIgnoreCase(type)).findAny().get();
    }

    public static void main(String[] args){
        System.out.println(getPropertyByType("IntegerType").propertyName);

    }
}
