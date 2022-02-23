package cucumber_blueprint.gql.steps.rick_morty;

import com.google.gson.Gson;
import cucumber_blueprint.data_containers.RestScenarioContext;
import cucumber_blueprint.gql.helpers.rick_morty.RickMortyGQLHelper;
import cucumber_blueprint.gql.response.character_response.Character;
import cucumber_blueprint.gql.response.character_response.CharacterResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import org.assertj.core.api.SoftAssertions;

import java.util.HashMap;

public class GqlExampleSteps {

    RestScenarioContext restScenarioContext;
    SoftAssertions assertions;
    RickMortyGQLHelper rickMortyGQLHelper;

    public GqlExampleSteps(RestScenarioContext restScenarioContext) {
        this.restScenarioContext = restScenarioContext;
        assertions = new SoftAssertions();
        rickMortyGQLHelper = new RickMortyGQLHelper(restScenarioContext);
    }


    @SneakyThrows
    @When("GetUser query is called")
    public void get_user_query_is_called() {
         //Create variables
        HashMap<String, String> variables = new HashMap<>();
        variables.put("id", "1");

        restScenarioContext.response = rickMortyGQLHelper.getCharacter(variables);
    }

    @Then("Response contains desired user data")
    public void response_contains_desired_user_data() {
        CharacterResponse response = new Gson().fromJson(restScenarioContext.response.asString(), CharacterResponse.class);
        Character character = response.getData().getCharacter();

        assertions.assertThat(character.getName()).isEqualTo("Rick Sanchez");
        assertions.assertThat(character.getStatus()).isEqualTo("Alive");
        assertions.assertThat(character.getGender()).isEqualTo("Male");
        assertions.assertAll();
    }


}
