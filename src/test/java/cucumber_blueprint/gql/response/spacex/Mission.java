package cucumber_blueprint.gql.response.spacex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission {

  private String description;
  private String id;
  private List<String> manufacturers;
  private String name;
  private String twitter;
  private String website;
  private String wikipedia;
//  private List<Payload> payloads;
}
