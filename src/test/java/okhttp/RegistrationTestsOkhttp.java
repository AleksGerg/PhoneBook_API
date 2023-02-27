package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class RegistrationTestsOkhttp {
    private final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccess() throws IOException {
        int i = new Random().nextInt(100) + 1000;
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("ben" + i + "@gmail.com")
                .password("Beny$123456")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void registrationWrong() throws IOException {
        int i = new Random().nextInt(100) + 1000;
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("ben" + i + "gmail.com")
                .password("Beny$123456")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder().url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        Assert.assertEquals(errorDto.getStatus(), 400);
        Assert.assertTrue(errorDto.getMessage().toString().contains("username=must be a well-formed email address"));
        Assert.assertFalse(response.isSuccessful());

    }
    @Test
    public void registrationWrongUserAlreadyExist() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("benb@gmail.com")
                .password("Beny$123456")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
       
        Assert.assertEquals(errorDto.getStatus(),409);
        Assert.assertTrue(errorDto.getMessage().toString().contains("User already exist"));
        Assert.assertFalse(response.isSuccessful());
    }
}
