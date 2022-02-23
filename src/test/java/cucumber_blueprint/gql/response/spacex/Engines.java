package cucumber_blueprint.gql.response.spacex;

import lombok.Data;

@Data
public class Engines{
	private String layout;
	private Integer number;
	private String propellant_1;
	private ThrustSeaLevel thrust_sea_level;
	private String engine_loss_max;
	private Integer thrust_to_weight;
	private ThrustVacuum thrust_vacuum;
	private String type;
	private String version;
	private String propellant_2;
}