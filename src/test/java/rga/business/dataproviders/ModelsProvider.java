package rga.business.dataproviders;

import com.google.gson.Gson;
import rga.business.entities.ModelEntity;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ModelsProvider {

    private static List<ModelEntity> getModelsData() {
        Gson gson = new Gson();
        BufferedReader bufferReader = null;
        try {
            bufferReader = new BufferedReader(new FileReader("src/test/resources/data/Models.json"));
            ModelEntity[] modelEntities = gson.fromJson(bufferReader, ModelEntity[].class);
            return Arrays.asList(modelEntities);
        }catch(FileNotFoundException e) {
            throw new RuntimeException("Json file not found at path : ");
        }finally {
            try { if(bufferReader != null) bufferReader.close();}
            catch (IOException ignore) {}
        }
    }

    public static final ModelEntity getModelByType(String type){
        return getModelsData().stream().filter(x -> x.type.equalsIgnoreCase(type)).findAny().get();
    }

    public static void main(String[] args){
        System.out.println(getModelByType("Object").modelType);

    }
}
