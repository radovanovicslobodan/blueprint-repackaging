package cucumber_blueprint.gql.response.spacex;

import lombok.Data;

@Data
public class FirstStage{
	private ThrustSeaLevel thrust_sea_level;
	private Double fuel_amount_tons;
	private ThrustVacuum thrust_vacuum;
	private Integer engines;
	private Integer burn_time_sec;
	private Boolean reusable;
}