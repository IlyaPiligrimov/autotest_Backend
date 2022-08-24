import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

public class CasesHomeworkTwoPostman extends AbstractTest{
    String complex = "recipes/complexSearch";
    String cuisine = "recipes/cuisine";
    @BeforeAll
    static void setUp(){

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @Test
    void getCuisine(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeInformation","true")
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200);
    }

    @Test
    void getRecieptOffset(){
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("offset", 0)
                .queryParam("number", 10)
                .queryParam("addRecipeInformation", true)
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusLine("HTTP/1.1 200 OK");

    }

    @Test
    void getRecieptOffsetCuisine(){

                 given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")

                .expect()
                .body("results",is(null))

                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200);

    }

    @Test
    void getDiet(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("diet", "vegetarian")
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)
                 .header("Connection", "keep-alive");

    }


    @Test
    void getAuthor(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("author", "coffeebean")
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)
                .header("Server","cloudflare");

    }
    @Test
    void getMaxCalories(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("maxCalories", 150)
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)
                .time(lessThan(2000L));

    }



    @Test
    void getMaxSugar(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam(" maxSugar", 10)
                .expect()
                .body("results",(is(notNullValue())))
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200);


    }
    @Test
    void getIntolerances(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("  intolerances", "gluten")
                .expect()
                .body("results",(is(notNullValue())))
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)
                .header("Server","cloudflare");

    }


    @Test
    void getIncludeIngredients(){

        given()
                .queryParam("apiKey", getApiKey())

                .queryParam("  includeIngredients", "spaghetti")
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)
                .header("Server","cloudflare")
        .time(lessThan(2000L));

    }



    @Test
    void getTypeNoAutorization(){

        given()
               // .queryParam("apiKey", getApiKey())

                .queryParam("type", "dessert")
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(401)
                .header("Server","cloudflare");


    }

    @Test
    void getType(){

        given()
                 .queryParam("apiKey", getApiKey())

                .queryParam("type", "dessert")
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)
                .header("Server","cloudflare");

    }

    @Test
    void getSort(){

        given()
                .queryParam("apiKey", getApiKey())

                .queryParam("sort", "calories")
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)
                .header("Content-Length","554");

    }



    @Test
    void getMinAlcohol(){

        given()
                .queryParam("apiKey", getApiKey())

                .queryParam("minAlcohol", 20)
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)
                .header("Content-Length","549");


    }

    @Test
    void getAddReception(){

        given()
                .queryParam("apiKey", getApiKey())

                .queryParam("addRecipeInformation", true)
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)
                .header("Allow-Control-Allow-Methods","GET, HEAD, POST, OPTIONS, DELETE, PUT")
                .header("Content-Length","9388");


    }

    @Test
    void getFillIngredients(){

        given()
                .queryParam("apiKey", getApiKey())

                .queryParam(" fillIngredients", true)
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)

                .header("CF-Cache-Status","DYNAMIC");
}
    @Test
    void getTwoSort(){

        given()
                .queryParam("apiKey", getApiKey())

                .queryParam("maxCalories", 200)
                .queryParam("maxProtein", 10)
                .when()
                .get(getBaseUrl()  +complex)
                .then()
                .statusCode(200)

                .header("Content-Length","658");
    }

    @Test
    void getTwoSortErrorUrl(){

        given()
                .queryParam("apiKey", getApiKey())

                .queryParam("maxCalories", 200)
                .queryParam("maxProtein", 10)
                .when()
                .get(getBaseUrl()  + complex + "9894")
                .then()
                .statusCode(404);

    }


    @Test
    void getThreeSort(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("type", "dessert")
                .queryParam("maxAlcohol", 18)
                .queryParam("maxCaffeine", 22)

                .when()
                .get(getBaseUrl()  + complex )
                .then()
                .statusCode(200);

    }

    @Test
    void getFourSort(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("minFluoride", 5)
                .queryParam("maxVitaminA", 77)
                .queryParam("minVitaminD", 33)
                .queryParam("maxVitaminK", 15)
                .when()
                .get(getBaseUrl()  + complex )
                .then()
                .header("alt-svc","h3=\":443\"; ma=86400, h3-29=\":443\"; ma=86400")
                .statusCode(200);

    }

    @Test
    void getTwoSortZeroResult(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("minFluoride", 5)
                .queryParam("maxVitaminA", 77)
                .when()
                .get(getBaseUrl()  + complex )
                .then()
                .header("Content-Length" ,"66")
                .statusCode(200);

    }
    @Test
    void getSortAndAddReciept(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeNutrition", true)
                .queryParam("maxVitaminA", 77)
                .when()
                .get(getBaseUrl()  + complex )
                .then()
                .header("Content-Type" ,"application/json")
                .header("Content-Length" ,"30582")
                .statusCode(200);

    }

    @Test
    void getPostCuisine(){

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","sushi")
                .expect()
                .body("cuisine",  equalTo("Japanese"))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200);
    }

    @Test
    void getPostCuisineSushi(){

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","sushi")
                .expect()
                .body("cuisine",  equalTo("Japanese"))
                .body("confidence",  equalTo(0.85f))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200);


    }
    @Test
    void getPostCuisinePizza(){

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","pizza")
                .expect()
                .body("cuisine",  equalTo("Mediterranean"))
                .body("confidence",  equalTo(0.95f))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .header("Connection", "keep-alive")
                .statusCode(200);

    }

    @Test
    void getPostCuisineIngrigientList(){

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("ingredientList","cheese")
                .expect()
                .body("cuisine",  equalTo("Mediterranean"))
                .body("confidence",  equalTo(0.0f))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200);
    }

    @Test
    void getPostCuisineBurger(){

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","burger")
                .expect()
                .body("cuisine",  equalTo("American"))
                .body("confidence",  equalTo(0.85f))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200);
    }

    @Test
    void getPostSushi(){

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","sushi")
                .expect()
                .body("confidence",  equalTo(0.85f))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .header("Connection", "keep-alive")
                .statusCode(200);
    }



    @Test
    void getPostCuisineMexican(){

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Cheesy Chicken Enchilada Quinoa Casserol")
                .expect()
                .body("cuisine",  equalTo("Mexican"))
                .body("confidence",  equalTo(0.85f))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200);
    }

    @Test
    void getPostCuisineThai(){

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Thai Pasta Salad")
                .expect()
                .body("cuisine",  equalTo("Asian"))
                .body("confidence",  equalTo(0.85f))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200);
    }

    @Test
    void getPostCuisineThaiNoAutorization(){

        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Thai Pasta Salad")

                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(401);
    }

    @Test
    void getPostCuisineGreek(){

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Berry Banana Breakfast Smoothie")
                .expect()
                .body("cuisine",  equalTo("Mediterranean"))
                .body("confidence",  equalTo(0.0f))
                .when()
                .post(getBaseUrl()+cuisine)
                .then()
                .statusCode(200);
    }


}
