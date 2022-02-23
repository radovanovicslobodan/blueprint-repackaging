package cucumber_blueprint.gql.response.spacex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThrustVacuum {

  private Integer lbf;
  private Integer kN;
}