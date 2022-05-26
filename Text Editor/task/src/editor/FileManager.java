package editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager {

    public static String readFileAsString(File txtFile) throws IOException {
        return new String(Files.readAllBytes(txtFile.toPath()));
    }

    public static void writeStringToFile(String text, File txtFile) {

        try (FileWriter writer = new FileWriter(txtFile)) {
            writer.write(text);
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }
}