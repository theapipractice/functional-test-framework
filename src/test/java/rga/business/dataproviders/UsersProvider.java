package rga.business.dataproviders;

import com.google.gson.Gson;
import rga.business.entities.UsersEntity;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UsersProvider {

    private static List<UsersEntity> getModelsData() {
        Gson gson = new Gson();
        BufferedReader bufferReader = null;
        try {
            bufferReader = new BufferedReader(new FileReader("src/test/resources/data/Users.json"));
            UsersEntity[] usersEntities = gson.fromJson(bufferReader, UsersEntity[].class);
            return Arrays.asList(usersEntities);
        }catch(FileNotFoundException e) {
            throw new RuntimeException("Json file not found at path : ");
        }finally {
            try { if(bufferReader != null) bufferReader.close();}
            catch (IOException ignore) {}
        }
    }

    public static final UsersEntity getUsersById(String id){
        return getModelsData().stream().filter(x -> x.id.equalsIgnoreCase(id)).findAny().get();
    }

    public static void main(String[] args){
        System.out.println(getUsersById("1").firstName);

    }
}
