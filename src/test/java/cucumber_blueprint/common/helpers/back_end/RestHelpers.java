package cucumber_blueprint.common.helpers.back_end;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestHelpers {
    /**
     * Generic method for sending Post request when request specification object
     * is inserted as parameter
     *
     * @param rSpec request specification object
     * @return response which can be used for assertion
     * @author: dino.rac
     */
    public static Response sendPostRequest(RequestSpecification rSpec) {

        return given().spec(rSpec).log().all().relaxedHTTPSValidation().when()
                .post().then().log().all().extract().response();
    }

    /**
     * Generic method for sending Get request when request specification object
     * is inserted as parameter
     *
     * @param rSpec request specification object
     * @return response which can be used for assertion
     * @author: dino.rac
     */
    public static Response sendGetRequest(RequestSpecification rSpec) {
        return given().spec(rSpec).log().all().relaxedHTTPSValidation().when()
                .get().then().log().all().extract().response();
    }

    /**
     * Generic method for sending Put request when request specification object
     * is inserted as parameter
     *
     * @param rSpec request specification object
     * @return response which can be used for assertion
     * @author: dino.rac
     */
    public static Response sendPut(RequestSpecification rSpec) {

        return given().spec(rSpec).log().all().relaxedHTTPSValidation().when()
                .put().then().log().all().extract().response();

    }

    /**
     * Generic method for sending Delete request when request specification object
     * is inserted as parameter
     *
     * @param rSpec request specification object
     * @return response which can be used for assertion
     * @author: dino.rac
     */

    public static Response sendDeleteRequest(RequestSpecification rSpec) {
        return given().spec(rSpec).log().all().relaxedHTTPSValidation().when()
                .delete().then().log().all().extract().response();

    }

     /**
     * Method will response when request specification and request method are inserted
     *
     * @param rSpec         RequestSpecification
     * @param requestMethod Name of request, check switch statement for desired value
     * @return Response
     * @author: dino.rac
     */
    public static Response sendRequest(RequestSpecification rSpec, String requestMethod) {
        Response response;

        switch (requestMethod) {
            case "GET":
                response = given().spec(rSpec).log().all().relaxedHTTPSValidation().when()
                        .get().then().log().all().extract().response();
                break;

            case "POST":
                response = given().spec(rSpec).log().all().relaxedHTTPSValidation().when()
                        .post().then().log().all().extract().response();
                break;

            case "PUT":
                response = given().spec(rSpec).log().all().relaxedHTTPSValidation().when()
                        .put().then().log().all().extract().response();
                break;

            case "DELETE":
                response = given().spec(rSpec).log().all().relaxedHTTPSValidation().when()
                        .delete().then().log().all().extract().response();
                break;

            default:
                throw new IllegalStateException(
                        "Unexpected value: " + requestMethod);

        }
        return response;
    }

}
