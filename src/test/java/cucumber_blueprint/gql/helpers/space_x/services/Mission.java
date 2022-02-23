package cucumber_blueprint.gql.helpers.space_x.services;

import lombok.Getter;

import java.util.List;
@Getter
public class Mission {
    private String description;
    private String id;
    private List<String> manufacturers;
    private String name;
    private String twitter;
    private String website;
    private String wikipedia;
}
