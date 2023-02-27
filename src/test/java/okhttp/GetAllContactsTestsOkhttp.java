package okhttp;

import com.google.gson.Gson;
import dto.AllContactsDto;
import dto.ContactDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsTestsOkhttp {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYmVuYkBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY3NzgyODU1NSwiaWF0IjoxNjc3MjI4NTU1fQ.7iHx2iH0jIWyzJ7qwvPq6O5ZG38uIEzjJFmnzUDWLFo";



    @Test
    public void getAllContactsSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization",token)
                .get().build();
        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());

        AllContactsDto allContactsDto =gson.fromJson(response.body().string(), AllContactsDto.class);

        List<ContactDto> contacts = allContactsDto.getContacts();

        for (ContactDto contact :contacts){
            System.out.println(contact.getId());
            System.out.println("*****");

        }
    }
}
