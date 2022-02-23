package cucumber_blueprint.gql.helpers.space_x;


import cucumber_blueprint.common.helpers.back_end.RestHelpers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SpaceXGqlClient {

  public static Response sendGqlRequest(RequestSpecification requestSpecification) {
    return new RestHelpers().sendPostRequest(requestSpecification);
  }
}
