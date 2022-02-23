package cucumber_blueprint.common.helpers.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;

public class GQLHelpers {
    /**
     * Parses the given graphql file object to the string suitable for the request
     * payload.
     *
     * @param file      - A {@link File} object
     * @param variables - The variables in the form of {@link ObjectNode}
     * @return A string suitable for the request payload.
     * @throws IOException
     */
    public static String parseGraphql(File file, String variables) throws IOException {
        String graphqlFileContent = convertInputStreamToString(new FileInputStream(file));
        return convertToGraphqlString(graphqlFileContent, variables);
    }

    private static String convertToGraphqlString(String graphql, String variables) throws JsonProcessingException {
        ObjectMapper oMapper = new ObjectMapper();
        ObjectNode oNode = oMapper.createObjectNode();
        try {
            oNode.put("query", graphql);
            oNode.put("variables", variables);
        }  catch (NullPointerException nullPointer){
            oNode.put("query", graphql);
        }
        return oMapper.writeValueAsString(oNode);
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    public static Response sendRequestWithToken(String uri, String token, String graphqlPayload, String path) {

        RequestSpecBuilder builder = new RequestSpecBuilder();

        //Prepare request
        builder.setBaseUri(uri);
        builder.setBasePath(path);
        builder.setContentType("application/json");
        builder.setBody(graphqlPayload);
        builder.addHeader("Authorization", token);
        RequestSpecification rSpec = builder.build();

        return RestHelpers.sendPostRequest(rSpec);
    }

    public static Response sendRequestWithoutToken(String uri, String graphqlPayload, String path) {

        RequestSpecBuilder builder = new RequestSpecBuilder();

        //Prepare request
        builder.setBaseUri(uri);
        builder.setBasePath(path);
        builder.setContentType("application/json");
        builder.setBody(graphqlPayload);
        RequestSpecification rSpec = builder.build();

        return RestHelpers.sendPostRequest(rSpec);
    }
}
