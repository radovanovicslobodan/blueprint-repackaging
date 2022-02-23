package cucumber_blueprint.gql.helpers.rick_morty;

import com.google.gson.Gson;
import cucumber_blueprint.common.helpers.back_end.GQLHelpers;
import cucumber_blueprint.data_containers.RestScenarioContext;
import io.restassured.response.Response;
import lombok.SneakyThrows;

import java.io.File;
import java.util.HashMap;

public class RickMortyGQLHelper {
    private RestScenarioContext restScenarioContext;
    private Gson gson = new Gson();
    private File gqlFile =
            new File("src/test/resources/gql/characterGQL.graphql");

    public RickMortyGQLHelper(RestScenarioContext restScenarioContext) {
        this.restScenarioContext = restScenarioContext;
    }

    @SneakyThrows
    public Response getCharacter(HashMap<String, String> variables)
             {

        String payload = GQLHelpers.parseGraphql(gqlFile,
                gson.toJson(variables));

        return GQLHelpers.sendRequestWithoutToken("https://rickandmortyapi.com", payload, "/graphql");
    }
}
