package cucumber_blueprint.common.helpers.excel;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;

@Slf4j
public class ExcelHelper {

    /**
     * @param excelFile - file to parse into beans(POJOs)
     * @param clazz     - bean
     * @param options   - parsing options
     * @param <T>       - type of bean
     * @return - list of beans(POJOs)
     */
    public <T> List<T> parseExcelFile(File excelFile, Class<T> clazz, PoijiOptions options) {
        if (excelFile == null) {
            throw new IllegalArgumentException("Excel file can't be null!");
        }

        final String fileExtension = FilenameUtils.getExtension(excelFile.getName());
        if (!(fileExtension.equalsIgnoreCase("xls") || fileExtension.equalsIgnoreCase("xlsx"))) {
            throw new IllegalArgumentException("Provided file is not excel type of file!");
        }

        log.info("Parsing excel file %s".formatted(excelFile.getName()));
        return Poiji.fromExcel(excelFile, clazz, options);
    }

    /**
     *
     * @param rowsToSkip - how many rows you want to skip, by default first row is skipped
     * @param sheetIndex - index position of the sheet in the excel file
     * @return - parsing options(settings)
     */
    public PoijiOptions crateParsingOptions(int rowsToSkip, int sheetIndex) {
        return PoijiOptions.PoijiOptionsBuilder.settings()
                .skip(rowsToSkip)
                .sheetIndex(sheetIndex)
                .build();
    }

    /**
     *
     * @param sheetIndex - index position of the sheet in the excel file
     * @return - parsing options that will override default behaviour and not skip first row, it will extract headers
     */
    public PoijiOptions createParsingOptionsWithHeaders(int sheetIndex){
        return PoijiOptions.PoijiOptionsBuilder.settings()
                .headerCount(0)
                .sheetIndex(sheetIndex)
                .build();
    }
}
