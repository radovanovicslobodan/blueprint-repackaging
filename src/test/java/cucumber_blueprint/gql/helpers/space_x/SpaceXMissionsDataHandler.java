package cucumber_blueprint.gql.helpers.space_x;

import cucumber_blueprint.common.helpers.back_end.graphqlv2.GraphQLMapper;
import cucumber_blueprint.common.helpers.back_end.graphqlv2.RequestType;
import cucumber_blueprint.gql.helpers.space_x.request.MissionsFind;
import cucumber_blueprint.gql.helpers.space_x.services.Mission;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SpaceXMissionsDataHandler {

  private final String baseUri;

  public SpaceXMissionsDataHandler(String baseUri) {
    this.baseUri = baseUri;
  }

  public RequestSpecification createMissionRequest() {


    String body = GraphQLMapper.requestBuilder(RequestType.QUERY, GqlMethods.MISSIONS.getMethodName())
        .responseClass(Mission.class)
        .build();
    return createRequestSpec(body);
  }

  public RequestSpecification createMissionRequest(MissionsFind find, Integer limit, Integer offset) {
    Map<String, Object> parameters = new HashMap<>();

    parameters.putIfAbsent("find", find);
    parameters.putIfAbsent("limit", limit);
    parameters.putIfAbsent("offset", offset);

    String body = GraphQLMapper.requestBuilder(RequestType.QUERY, GqlMethods.MISSIONS.getMethodName())
        .parameter(parameters)
        .responseClass(Mission.class)
//        .recursionDepth(4)
        .build();

    return createRequestSpec(body);
  }

  public Mission[] parseMissionsFromResponse(Response response) {
    return GraphQLMapper.parseDataObject(response, GqlMethods.MISSIONS.getMethodName(), Mission[].class);
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
