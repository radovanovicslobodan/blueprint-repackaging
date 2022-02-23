package cucumber_blueprint.common.helpers.environment;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class YamlReader {

    private static Logger logger = LoggerFactory.getLogger(YamlReader.class);

    /**
     * When environment name is inserted it will return Env object with properties from yaml file
     *
     * @param envName desired environment name
     * @return Env object with values from yml file
     * @author dino.rac
     */
    public static Env getEnvironmentFromYaml(String envName) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Env environment = mapper.readValue
                (new File("src/test/resources/properties/" + envName + "-properties.yml"), Env.class);

        return environment;


    }

}
