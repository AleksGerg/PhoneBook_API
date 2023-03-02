package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthResponseDto;
import dto.ContactDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class AddNewContactRA {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYmVuYkBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY3ODM1MTkzNSwiaWF0IjoxNjc3NzUxOTM1fQ.Y-gbY18BJB7uWFJxVNRlPuWIQJHrPikVxXfmPKBr84s";

    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI="https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath="v1";
    }

    @Test
    public void addNewContactTest(){
        int i = new Random().nextInt(100)+1000;

        ContactDto newContact = ContactDto.builder()
                .name("Avokado")
                .lastName("Green")
                .email("avaG@gmail.com")
                .phone("6325987412")
                .address("Netanya")
                .description("Fruit").build();
        given()
                .header("Authorization",token)
                .body(newContact)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200);



    }
    @Test
    public void addNewContactIncorrectName(){
        ContactDto newContact = ContactDto.builder()

                .lastName("Green")
                .email("avaG@gmail.com")
                .phone("6325987412")
                .address("Netanya")
                .description("Fruit").build();

        given()
                .header("Authorization",token)
                .body(newContact)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(400);
               //.assertThat().body("massage.name",containsString("Name cant be empty!")); No Message in application!
    }

    @Test
    public void addNewContactIncorrectPhone(){
        ContactDto newContact = ContactDto.builder()
                .name("Avokado")
                .lastName("Green")
                .email("avaG@gmail.com")
                .phone("632")
                .address("Netanya")
                .description("Fruit").build();

        given()
                .header("Authorization",token)
                .body(newContact)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("massage.phone",containsString("Phone number must contain only digits! And length min 10, max 15!"));
    }

    @Test
    public void addNewContactUnauthorizedUser(){
        ContactDto newContact = ContactDto.builder()
                .name("Avokado")
                .lastName("Green")
                .email("avaG@gmail.com")
                .phone("632")
                .address("Netanya")
                .description("Fruit").build();

        given()
                .header("Authorization",token+1)
                .body(newContact)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(401);
    }

}
