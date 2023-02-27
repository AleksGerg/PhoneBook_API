package okhttp;

import com.google.gson.Gson;
import dto.ContactDto;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class AddNewContactsOkhttp {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYmVuYkBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY3NzgyODU1NSwiaWF0IjoxNjc3MjI4NTU1fQ.7iHx2iH0jIWyzJ7qwvPq6O5ZG38uIEzjJFmnzUDWLFo";
    private final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    @Test
    public void addNewContactSuccess(){
        int i = new Random().nextInt(1000)+1000;
        ContactDto dto = ContactDto.builder()
                .name("Mia")
                .lastName("Expt")
                .email("mia"+i+"@mail.com")
                .phone("123659874521")
                .address("NY")
                .description("Friend")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(dto),JSON);

    }


}
