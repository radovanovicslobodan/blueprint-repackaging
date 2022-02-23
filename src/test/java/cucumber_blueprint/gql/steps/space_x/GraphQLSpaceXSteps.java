package cucumber_blueprint.gql.steps.space_x;

import cucumber_blueprint.data_containers.RestScenarioContext;
import cucumber_blueprint.gql.helpers.space_x.SpaceXGqlClient;
import cucumber_blueprint.gql.helpers.space_x.SpaceXMissionsDataHandler;
import cucumber_blueprint.gql.helpers.space_x.SpaceXRocketsDataHandler;
import cucumber_blueprint.gql.helpers.space_x.request.MissionsFind;
import cucumber_blueprint.gql.helpers.space_x.services.Mission;
import cucumber_blueprint.gql.response.spacex.Rocket;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphQLSpaceXSteps {

    private RestScenarioContext restScenarioContext;
    private SpaceXMissionsDataHandler missionDataHandler;
    private SpaceXRocketsDataHandler rocketsDataHandler;


    public GraphQLSpaceXSteps(RestScenarioContext restScenarioContext) {
        this.restScenarioContext = restScenarioContext;
        missionDataHandler = new SpaceXMissionsDataHandler("https://api.spacex.land/graphql");
        rocketsDataHandler = new SpaceXRocketsDataHandler("https://api.spacex.land/graphql");
    }

    @Given("I create GraphQl query request")
    public void iCreateGraphQlQueryRequest() {
        restScenarioContext.rSpec = missionDataHandler.createMissionRequest();
    }

    @When("I send GraphQl request")
    public void iSendGraphQlRequest() {
       restScenarioContext.response =
                SpaceXGqlClient.sendGqlRequest(restScenarioContext.rSpec);
    }

    @Then("I receive response with data")
    public void iReceiveResponseWithData() {
        Mission[] missions = missionDataHandler.parseMissionsFromResponse(
                restScenarioContext.response);

        assertTrue(missions.length > 0);
    }

    @Given("I create GraphQl query request with filter")
    public void iCreateGraphQlQueryRequestWithFilter() {
        MissionsFind find = MissionsFind.builder()
                .manufacturer("Orbital ATK")
                .build();
        restScenarioContext.rSpec = missionDataHandler.createMissionRequest(find, 2,2);
    }


    @Given("I create GraphQl query Rocket request")
    public void iCreateGraphQlQueryRocketRequest() {
        restScenarioContext.rSpec = rocketsDataHandler.createRocketsRequest();
    }

    @Then("I receive response with rocket data")
    public void iReceiveResponseWithRocketData() {
        Rocket[] rockets = rocketsDataHandler.parseRockerFromResponse(
                restScenarioContext.response);

        assertTrue(rockets.length > 0);
    }
}
