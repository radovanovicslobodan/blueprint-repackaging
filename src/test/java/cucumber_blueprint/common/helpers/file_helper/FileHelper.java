package cucumber_blueprint.common.helpers.file_helper;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Slf4j
public class FileHelper {

    /**
     * @param sourcePath - file that we want to move/rename
     * @param destPath   - file that represent the moved file or a same file just with a new name
     */
    public static void moveFile(String sourcePath, String destPath) {
        if (Files.exists(Path.of(sourcePath)) && Files.exists(Path.of(destPath))) {
            try {
                Files.move(Path.of(sourcePath), Path.of(destPath));
            } catch (IOException e) {
                log.error("Something went wrong while moving/renaming the file!");
                log.error("Invalid permissions - check do you have appropriate permissions!");
                e.printStackTrace();
            }
        } else {
            throw new FileSystemNotFoundException("One or both files provided does not exists!");
        }
    }

    /**
     * @param sourceFile      - Java representation of a file(file, folder or directory, etc.) we want to copy
     * @param destinationFile - Java representation of a file(file, folder or directory, etc.) location of a file where we want to make a copy
     */
    public void copyFile(File sourceFile, File destinationFile) {
        if (sourceFile.exists() && destinationFile.exists()) {
            try {
                log.info("Starting to copy file %s to the file %s.".formatted(sourceFile.getName(), destinationFile.getName()));
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                log.info("File %s has been successfully copied to the file %s.".formatted(sourceFile.getName(), destinationFile.getName()));
            } catch (IOException e) {
                log.error("Something went wrong while copying the file!");
                e.printStackTrace();
            }
        } else {
            throw new FileSystemNotFoundException("One or both files provided does not exists!");
        }
    }

    /**
     * @param sourcePath      - string representation of a file path (location) we want to copy
     * @param destinationPath - string representation of a file path (location) of a file where we want to make a copy
     */
    public void copyFile(String sourcePath, String destinationPath) {
        if (Files.exists(Path.of(sourcePath)) && Files.exists(Path.of(destinationPath))) {
            File sourceFile = createFile(sourcePath);
            File destinationFile = createFile(destinationPath);
            copyFile(sourceFile, destinationFile);
        } else {
            throw new FileSystemNotFoundException("One or both files provided does not exists!");
        }
    }

    /**
     * @param filePath - path to a file
     * @return Java representation of a file
     */
    public File createFile(String filePath) {
        return createPath(filePath).toFile();
    }

    /**
     * @param filePath - path to a file
     * @return Java (NIO lib) representation of a path(file)
     */
    public Path createPath(String filePath) {
        Path file = null;
        try {
            file = Files.createFile(Path.of(filePath));
            log.info("Creating file %s.".formatted(filePath));
        } catch (IOException e) {
            log.error("Something went wrong while copying the file!");
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @param filePath - path to a file
     */
    public void deleteFileQuietly(String filePath) {
        log.info("Deleting file %s.".formatted(filePath));
        try {
            Files.delete(Path.of(filePath));
            log.info("File has been deleted.");
        } catch (IOException e) {
            log.error("Something went wrong while deleting the file!");
            log.error("Invalid permissions - check do you have appropriate permissions!");
            e.printStackTrace();
        }
    }

    /**
     * @param filePath - path to a file
     * @return result of deletion
     */
    public boolean deleteFile(String filePath) {
        log.info("Deleting file %s.".formatted(filePath));
        boolean isDeleted = false;
        try {
            isDeleted = Files.deleteIfExists(Path.of(filePath));
            log.info("File has been successfully deleted.");
        } catch (NoSuchFileException e) {
            log.error("No such file/directory exists!");
            e.printStackTrace();
        } catch (DirectoryNotEmptyException e) {
            log.error("Directory is not empty!");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("Something went wrong while deleting the file!");
            log.error("Invalid permissions - check do you have appropriate permissions!");
            e.printStackTrace();
        }
        return isDeleted;
    }
}
