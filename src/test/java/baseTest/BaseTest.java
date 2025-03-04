package baseTest;

import io.restassured.RestAssured;
import api.specification.RequestSpec;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setup() {
        RestAssured.requestSpecification = RequestSpec.requestSpec("https://petstore.swagger.io/v2");
    }

}