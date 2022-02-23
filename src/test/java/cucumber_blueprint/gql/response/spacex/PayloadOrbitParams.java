package cucumber_blueprint.gql.response.spacex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayloadOrbitParams {

  private Double apoapsis_km;
  private Double arg_of_pericenter;
  private Double eccentricity;
  private String epoch;
  private Double inclination_deg;
  private Double lifespan_years;
  private Double longitude;
  private Double mean_anomaly;
  private Double mean_motion;
  private Double periapsis_km;
  private Double period_min;
  private Double raan;
  private String reference_system;
  private String regime;
  private Double semi_major_axis_km;
}
