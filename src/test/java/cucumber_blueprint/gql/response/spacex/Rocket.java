package cucumber_blueprint.gql.response.spacex;

import lombok.Data;

import java.util.List;

@Data
public class Rocket {
	private SecondStage second_stage;
	private String country;
	private Mass mass;
	private String description;
	private Integer cost_per_launch;
	private Boolean active;
	private String type;
	private List<PayloadWeightsItem> payload_weights;
	private String first_flight;
	private LandingLegs landing_legs;
	private Diameter diameter;
	private FirstStage first_stage;
	private Engines engines;
	private Integer stages;
	private String name;
	private Integer boosters;
	private String company;
	private String wikipedia;
	private Integer success_rate_pct;
	private String id;
	private Height height;
}