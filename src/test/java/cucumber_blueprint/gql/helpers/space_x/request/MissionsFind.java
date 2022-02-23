package cucumber_blueprint.gql.helpers.space_x.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionsFind {

    private String id;
    private String manufacturer;
    private String name;
    private String payload_id;
}
