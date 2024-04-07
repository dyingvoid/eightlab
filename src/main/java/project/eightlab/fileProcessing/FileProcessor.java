package project.eightlab.fileProcessing;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileProcessor {
    void process(String text) throws IOException;
}
