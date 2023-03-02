package okhttp;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ContactResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class AddNewContactsOkhttp {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYmVuYkBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY3NzgyODU1NSwiaWF0IjoxNjc3MjI4NTU1fQ.7iHx2iH0jIWyzJ7qwvPq6O5ZG38uIEzjJFmnzUDWLFo";
    private final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    @Test
    public void addNewContactSuccess() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        ContactDto dto = ContactDto.builder()
                .name("Mia")
                .lastName("Expt")
                .email("miae"+i+"@mail.com")
                .phone("123659871"+i)
                .address("NY")
                .description("Friend")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(dto),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body).build();
        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        ContactResponseDto resDto = gson.fromJson(response.body().string(), ContactResponseDto.class);
        Assert.assertTrue(resDto.getMessage().contains("Contact was added"));
    }

    @Test
    public  void addNewContactWrongName() throws IOException {
        ContactDto dto = ContactDto.builder()
                .lastName("Param")
                .email("par@gmail.com")
                .address("NY")
                .description("Friend").build();

        RequestBody body = RequestBody.create(gson.toJson(dto),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body).build();

        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage().toString(),"{name=must not be blank}");
    }
    @Test
    public void addNewContactWrongEmail() throws IOException {
        ContactDto dto = ContactDto.builder()
                .name("Mia")
                .lastName("Dow")
                .email("miamail.com")
                .phone("12345698745")
                .description("Friend").build();

        RequestBody body = RequestBody.create(gson.toJson(dto),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body).build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage().toString(),"{email=must be a well-formed email address}");

    }

}
