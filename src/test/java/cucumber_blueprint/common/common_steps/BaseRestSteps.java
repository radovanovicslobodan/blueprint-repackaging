package cucumber_blueprint.common.common_steps;

import cucumber_blueprint.data_containers.RestScenarioContext;
import org.assertj.core.api.SoftAssertions;

public abstract class BaseRestSteps {
    protected RestScenarioContext restScenarioContext;
    protected SoftAssertions assertions;

    public BaseRestSteps(RestScenarioContext restScenarioContext) {
        this.restScenarioContext = restScenarioContext;
        this.assertions = new SoftAssertions();
    }
}
