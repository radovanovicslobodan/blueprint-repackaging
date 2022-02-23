package cucumber_blueprint.common.helpers.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CsvHelper {

    /**
     * @param csvFile     - file to parse into beans(POJOs)
     * @param clazz       - bean or POJO
     * @param linesToSkip - how many rows you want to skip(when bindByName header row must remain, when bindByPosition header row can be skipped)
     * @param <T>         - type of bean
     * @return - list of beans
     */
    public <T> List<T> parseCsvFile(File csvFile, Class<T> clazz, int linesToSkip) {
        if (csvFile == null) {
            throw new IllegalArgumentException("CSV file can't be null!");
        }

        final String fileExtension = FilenameUtils.getExtension(csvFile.getName());
        if (!fileExtension.equalsIgnoreCase("csv")) {
            throw new IllegalArgumentException("Provided file is not CSV type of file!");
        }

        log.info("Parsing CSV file %s".formatted(csvFile.getName()));
        List<T> beans = new ArrayList<>();
        try {
            beans = new CsvToBeanBuilder<T>(new BufferedReader(new FileReader(csvFile)))
                    .withSkipLines(linesToSkip)
                    .withType(clazz)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            log.error("Something went wrong while reading the file!");
            log.error("Invalid permissions - check do you have appropriate permissions!");
            e.printStackTrace();
        }
        if (beans.size() > 0) {
            log.info("CSV file %s has been successfully parsed to objects of class %s.".formatted(csvFile.getName(), clazz.getName()));
        }
        return beans;
    }

    /**
     * @param csvFile - file to parse into strings, array of strings
     * @return - list of array strings, where each array represents a row
     */
    public List<String[]> parseCsvFile(File csvFile) {
        if (csvFile == null) {
            throw new IllegalArgumentException("CSV file can't be null!");
        }

        final String fileExtension = FilenameUtils.getExtension(csvFile.getName());
        if (!fileExtension.equalsIgnoreCase("csv")) {
            throw new IllegalArgumentException("Provided file is not CSV type of file!");
        }

        String[] row;
        List<String[]> rows = new ArrayList<>();
        log.info("Parsing CSV file %s".formatted(csvFile.getName()));
        try {
            CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(csvFile)));
            while ((row = csvReader.readNext()) != null) {
                rows.add(row);
            }
        } catch (FileNotFoundException e) {
            log.error("Something went wrong while reading the file!");
            log.error("Invalid permissions - check do you have appropriate permissions!");
            e.printStackTrace();
        } catch (CsvValidationException e) {
            log.error("Something went wrong while parsing the CSV file!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (rows.size() > 0) {
            log.info("CSV file %s has been successfully parsed.".formatted(csvFile.getName()));
        }
        return rows;
    }

    /**
     * @param csvFile - file from which we want to extract header values from first row
     * @return - list of strings holding header values
     */
    public List<String> getHeadersFromCsvFile(File csvFile) {
        if (csvFile == null) {
            throw new IllegalArgumentException("CSV file can't be null!");
        }

        final String fileExtension = FilenameUtils.getExtension(csvFile.getName());
        if (!fileExtension.equalsIgnoreCase("csv")) {
            throw new IllegalArgumentException("Provided file is not CSV type of file!");
        }
        List<String[]> rows = parseCsvFile(csvFile);
        String[] headerRow = rows.get(0);
        return Arrays.asList(headerRow);
    }
}
