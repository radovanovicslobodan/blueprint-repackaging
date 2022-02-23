package cucumber_blueprint.gql.response.spacex;

import lombok.Data;

@Data
public class PayloadWeightsItem{
	private Integer lb;
	private String name;
	private String id;
	private Integer kg;
}