package project.eightlab.fileProcessing;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileProcessor {
    void process(String path) throws IOException;
}
