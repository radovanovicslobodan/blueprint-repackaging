package cucumber_blueprint.common.helpers.cukedoctor;


import com.github.cukedoctor.Cukedoctor;
import com.github.cukedoctor.api.CukedoctorConverter;
import com.github.cukedoctor.api.DocumentAttributes;
import com.github.cukedoctor.api.model.Feature;
import com.github.cukedoctor.config.GlobalConfig;
import com.github.cukedoctor.parser.FeatureParser;
import com.github.cukedoctor.util.FileUtil;

import java.util.List;
public class GenerateDoc {
    /**
     * Method will generate adoc file from feature file
     */
    public static void generate(){
        List<String> pathToCucumberJsonFiles = FileUtil.findJsonFiles("target/cucumber.json");
        List<Feature> features = FeatureParser.parse(pathToCucumberJsonFiles);
        DocumentAttributes attrs = GlobalConfig.getInstance().getDocumentAttributes();
        attrs.toc("left").backend("html5")
                .docType("book")
                .icons("font").numbered(false)
                .sourceHighlighter("coderay")
                .docTitle("Documentation Title")
                .sectAnchors(true).sectLink(true);

        CukedoctorConverter converter = Cukedoctor.instance(features, attrs);
        converter.setFilename("target/living_doc/living_documentation.adoc");

        converter.saveDocumentation();
        System.out.println("Your document is created");
    }

}
