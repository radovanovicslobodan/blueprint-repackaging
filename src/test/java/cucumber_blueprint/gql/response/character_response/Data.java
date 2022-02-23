package cucumber_blueprint.gql.response.character_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Data {
    @SerializedName("character")
    @Expose
    private Character character;
}
