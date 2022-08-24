import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;






public class HomeWorkTest extends AbstractTest {
    String complex = "recipes/complexSearch";
    String cuisine = "recipes/cuisine";
    @BeforeAll
    static void setUp(){

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @Test
    void getExcludeCuisine(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("excludeCuisine", "Thai")
                .when()
                .get(getBaseUrl() + complex)
                .then()
                .statusCode(200);

    }
    @Test
    void getType(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("type", "marinade")
                .when()
                .get(getBaseUrl() + complex)
                .then()
                .statusLine("HTTP/1.1 200 OK")
                .header("Connection", "keep-alive")
                .time(lessThan(2000L));

    }

    @Test
    void getRecieptInfo(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .expect()
                .body("vegetarian", is(false))
                .body("vegan", is(false))
                .body("license", equalTo("CC BY-SA 3.0"))
                .body("pricePerServing", equalTo(163.15F))
                .body("extendedIngredients[0].aisle", equalTo("Milk, Eggs, Other Dairy"))
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information");

    }
    @Test
    void getRecieptMaxSugar(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("maxSugar", 0)
                .expect()
                .body("offset",  equalTo(0))
                .body("number",  equalTo(10))
                .body("totalResults", equalTo(0))
                .when()
                .get(getBaseUrl() + complex);

    }

    @Test
    void getRecieptNoAutorization() {

        given()

                .queryParam("ignorePantry", true)
                .when()
                .get(getBaseUrl() + complex)
                .then()
                .statusCode(401);

    }
    @Test
    void getPostRequestBurger() {

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","burger")
                .expect()
                .body("cuisine",  equalTo("American"))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200);
}
    @Test
    void getPostRequestSushi() {

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","sushi")
                .expect()
                .body("cuisine",  equalTo("Japanese"))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200)
                .header("Connection", "keep-alive");

    }

    @Test
    void getPostRequestPizza() {

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","pizza")
                .expect()
                .body("cuisine",  equalTo("Italian"))
                .body("confidence",  equalTo(0.95F))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200)
                .header("Connection", "keep-alive");

    }


    @Test
    void getPostRequestIngredientList() {

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("ingredientList","garlic")
                .expect()
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200)
                .header("Connection", "keep-alive")
        .header("Server","cloudflare");

    }
    @Test
    void getPostRequestSushiNoAutorization() {

        given()
                .queryParam("apiKey", getApiKey()+"434")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","sushi")
                .expect()
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(401)
                .header("Connection", "keep-alive");

    }

}


