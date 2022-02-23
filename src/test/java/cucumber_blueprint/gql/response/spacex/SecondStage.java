package cucumber_blueprint.gql.response.spacex;

import lombok.Data;

@Data
public class SecondStage{
	private Double fuel_amount_tons;
	private Payloads payloads;
	private Integer engines;
	private Integer burn_time_sec;
	private Thrust thrust;
}