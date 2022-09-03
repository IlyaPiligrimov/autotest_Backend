package homework5;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateProductTest {
    static ProductService productService;
    Product product = null;
    int id;
    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle("Fish and chips")
                .withCategoryTitle("Food")
                .withPrice(50);
    }

    @Test
    void createProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();
        assertThat(response.body().getTitle(), equalTo("Fish and chips"));
    }

    @Test
    void modifyProduct() throws IOException {


        Response<Product> response = productService.modifyProduct(product.withPrice(200))
                .execute();

        assertThat(response.body().getPrice(), equalTo(200));
    }
    @Test
    void getProductById() throws IOException {
        Response<Product> response = productService.getProductById(2)
                .execute();
        assertThat(response.body().getTitle(), equalTo("Bread"));
    }

    @Test
    void getProducts() throws IOException {
        Response<ResponseBody> response = productService.getProducts()
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

  @Test
    void delCreateProduct() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
    }

}