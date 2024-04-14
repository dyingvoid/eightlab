package project.eightlab.fileProcessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileProcessorFactory {
    private final List<FileProcessor> fileProcessorList;

    @Autowired
    public FileProcessorFactory(List<FileProcessor> fileProcessorList) {
        this.fileProcessorList = fileProcessorList;
    }


    public FileProcessor getProccessor(String path) {
        for (FileProcessor fileProcessor : fileProcessorList) {
            if (fileProcessor.isSupported(path)) {
                return fileProcessor;
            }
        }

        return null;
    }
}
