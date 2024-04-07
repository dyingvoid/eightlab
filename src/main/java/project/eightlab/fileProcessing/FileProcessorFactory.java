package project.eightlab.fileProcessing;

import jakarta.activation.MimetypesFileTypeMap;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileProcessorFactory {
    private final DirectoryProcessor directoryProcessor;
    private final ImageProcessor imageProcessor;
    private final MusicProcessor musicProcessor;
    private final TextProcessor textProcessor;

    @Autowired
    public FileProcessorFactory(DirectoryProcessor directoryProcessor,
                                ImageProcessor imageProcessor,
                                MusicProcessor musicProcessor,
                                TextProcessor textProcessor) {
        this.directoryProcessor = directoryProcessor;
        this.imageProcessor = imageProcessor;
        this.musicProcessor = musicProcessor;
        this.textProcessor = textProcessor;
    }


    public FileProcessor getProccessor(String path){
        File file = new File(path);

        if(file.isDirectory())
            return this.directoryProcessor;
        if(FileNameUtils.getExtension(path).equals("mp3"))
            return this.musicProcessor;
        if(FileNameUtils.getExtension(path).equals("txt"))
            return this.textProcessor;
        if(new MimetypesFileTypeMap()
                .getContentType(file)
                .split("/")[0].equals("image"))
            return this.imageProcessor;

        return null;
    }
}
