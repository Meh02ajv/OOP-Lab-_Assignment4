import java.io.IOException;

public interface FileOperations {
    void saveToFile(String filename) throws IOException;
    void loadFromFile(String filename) throws IOException;
}
