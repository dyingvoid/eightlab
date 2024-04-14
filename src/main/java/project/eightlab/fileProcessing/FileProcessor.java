package project.eightlab.fileProcessing;


import java.io.IOException;

public interface FileProcessor {
    boolean isSupported(String path);
    String getDescription();
    void process(String path, int option) throws IOException;
}
