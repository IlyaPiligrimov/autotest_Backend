import org.junit.jupiter.api.Test;


public class HomeWorkTest extends AbstractTest{
    @Test
    void getExcludeCuisine(){
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("excludeCuisine", true)
                .when()
                .get(getBaseUrl() + "/recipes/complexSearch")
                .then()
                .statusCode(200);

    }


}
