import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


public class HomeWorkTest extends AbstractTest {
    String complex = "recipes/complexSearch";
    String cuisine = "recipes/cuisine";


    @BeforeAll
    static void setUp(){

    }




    @Test
    void getRecipePositiveTest() {
        RestAssured.responseSpecification = responseSpecification;
        given()
                .spec(requestSpecification)
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .then()
                .spec(responseSpecification);
    }





    @Test
    void getExcludeCuisine(){

        given()
                .spec(requestSpecification)
                .queryParam("excludeCuisine", "Thai")
                .when()
                .get(getBaseUrl() + complex)
                .then()
                .spec(responseSpecification);

    }
    @Test
    void getType(){

        given()
                .spec(requestSpecification)
                .queryParam("type", "marinade")
                .when()
                .get(getBaseUrl() + complex)
                .then()
                .spec(responseSpecification);

    }

    @Test
    void getRecieptInfo(){

        given()
                .spec(requestSpecification)
                .expect()
                .body("vegetarian", is(false))
                .body("vegan", is(false))
                .body("license", equalTo("CC BY-SA 3.0"))
                .body("pricePerServing", equalTo(163.15F))
                .body("extendedIngredients[0].aisle", equalTo("Milk, Eggs, Other Dairy"))
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .then()
                .spec(responseSpecification);

    }
    @Test
    void getRecieptMaxSugar(){

        given()
                .spec(requestSpecification)
                .queryParam("maxSugar", 0)
                .expect()
                .body("offset",  equalTo(0))
                .body("number",  equalTo(10))
                .body("totalResults", equalTo(0))
                .when()
                .get(getBaseUrl() + complex)
                .then()
                .spec(responseSpecification);

    }

    @Test
    void getRecieptNoAutorization() {

          given().spec(requestSpecificationNo)
                .queryParam("ignorePantry", true)
                .when()
                .get(getBaseUrl() + complex)
                .then()
                  .spec(responseSpecificationNo);
    }
    @Test
    void getPostRequestBurger() {

        given()
                .spec(requestSpecification)
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","burger")
                .expect()
                .body("cuisine",  equalTo("American"))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .spec(responseSpecification);
}
    @Test
    void getPostRequestSushi() {

        given()
                .spec(requestSpecification)
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","sushi")
                .expect()
                .body("cuisine",  equalTo("Japanese"))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .spec(responseSpecification);

    }

    @Test
    void getPostRequestPizza() {

        given()
                .spec(requestSpecification)
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","pizza")
                .expect()
                .body("cuisine",  equalTo("Mediterranean"))
                .body("confidence",  equalTo(0.95F))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .spec(responseSpecification);

    }


    @Test
    void getPostRequestIngredientList() {

        given()
                .spec(requestSpecification)
                .contentType("application/x-www-form-urlencoded")
                .formParam("ingredientList","garlic")
                .expect()
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .spec(responseSpecification)
                .header("Connection", "keep-alive")
        .header("Server","cloudflare");

    }
    @Test
    void getPostRequestSushiNoAutorization() {

        given().
                spec(requestSpecificationNo)
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","sushi")
                .expect()
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .spec(responseSpecificationNo);

    }




}


