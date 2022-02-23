package cucumber_blueprint.gql.response.spacex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payload {

  private List<String> customers;
  private String id;
  private String manufacturer;
  private String nationality;
  private List<Integer> norad_id;
  private PayloadOrbitParams orbit_params;
  private String orbit;
  private Double payload_mass_kg;
  private Double payload_mass_lbs;
  private String payload_type;
  private Boolean reused;
}
