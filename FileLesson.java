
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileLesson {

    public static void backupFiles(String path) {
        Path source = Paths.get(path);
        Path backup = Paths.get(path, "backup");
        try {
            Files.createDirectories(backup);
            try (Stream<Path> files = Files.list(source)) {
                files.filter(Files::isRegularFile).forEach(file -> {
                    try {
                        Files.copy(file, backup.resolve(file.getFileName()));
                    } catch (IOException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                });
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static void encode(int[] gameField, String filePath) {
        byte[] bytes = new byte[3]; 
        int index = 0;
        for (int i = 0; i < gameField.length; i++) {
            int value = gameField[i];
            bytes[index / 4] |= value << (2 * (index % 4));
            index++;
        }
        
        try {
            Files.write(Paths.get(filePath), bytes);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        backupFiles("./test");
        int[] gameField = {
            1, 2, 0, 
            1, 0, 2, 
            1, 0, 3
        };
        encode(gameField, "./result.txt");
    }
}
