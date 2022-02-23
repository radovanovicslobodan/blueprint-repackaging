package cucumber_blueprint.common.helpers.ui;

import cucumber_blueprint.data_containers.CommonProperties;

public class Urls {
    public static String login() {
        return CommonProperties.webUri + "/#/login";
    }
}
