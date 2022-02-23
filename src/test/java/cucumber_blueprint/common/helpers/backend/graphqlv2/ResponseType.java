package cucumber_blueprint.common.helpers.backend.graphqlv2;

import lombok.Getter;

@Getter
public enum ResponseType {
  DATA("data"),
  ERRORS("errors");

  private String value;

  ResponseType(String value) {
    this.value = value;
  }
}
