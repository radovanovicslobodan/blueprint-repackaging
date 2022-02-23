package cucumber_blueprint.gql.helpers.space_x;

import cucumber_blueprint.common.helpers.backend.graphqlv2.GraphQLMapper;
import cucumber_blueprint.common.helpers.backend.graphqlv2.RequestType;
import cucumber_blueprint.gql.response.spacex.Rocket;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpaceXRocketsDataHandler {

  private final String baseUri;

  public SpaceXRocketsDataHandler(String baseUri) {
    this.baseUri = baseUri;
  }

  public RequestSpecification createRocketsRequest() {

    String body = GraphQLMapper.requestBuilder(RequestType.QUERY, GqlMethods.ROCKETS.getMethodName())
        .responseClass(Rocket.class)
        .build();
    return createRequestSpec(body);
  }


  public Rocket[] parseRockerFromResponse(Response response) {
    return GraphQLMapper.parseDataObject(response, GqlMethods.ROCKETS.getMethodName(), Rocket[].class);
  }

  private RequestSpecification createRequestSpec(String requestBody) {
    log.info("Building GQL request.");
    RequestSpecification request = new RequestSpecBuilder()
        .setBaseUri(baseUri)
        .setContentType(ContentType.JSON)
        .setBody(requestBody)
        .build();

    log.info("GQL request is built.");
    return request;
  }
}
