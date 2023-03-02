package okhttp;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ContactResponseDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIdOkhttp {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "";
    private final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    String id;

    @BeforeMethod
    public void precondition() throws IOException {


        int i = new Random().nextInt(100)+1000;
    ContactDto dto = ContactDto.builder()
            .name("Mia")
            .lastName("Dow")
            .email("miaD@gmail.com")
            .phone("123456985")
            .address("NY")
            .description("Friend").build();

    RequestBody body = RequestBody.create(gson.toJson(dto),JSON);
    Request request = new Request.Builder()
            .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
            .addHeader("Authorization", token)
            .post(body).build();
    Response response = client.newCall(request).execute();
    Assert.assertTrue(response.isSuccessful());

        ContactResponseDto resDto = gson.fromJson(response.body().string(), ContactResponseDto.class);
        System.out.println(resDto.getMessage()); // Contact was added! ID: 5576b4a8-deed-4a73-9b49-37d8b126a8f0

        String massege = resDto.getMessage();
        String[]all = massege.split(": ");
        id = all[1];
    }

    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+id)
                .delete()
                .addHeader("Authorization",token).build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        ContactResponseDto resdto = gson.fromJson(response.body().string(), ContactResponseDto.class);
        Assert.assertEquals(resdto.getMessage(),"Contact was deleted!");
    }
}
