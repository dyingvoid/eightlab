package project.eightlab.fileProcessing;

import lombok.SneakyThrows;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;

@Component
public class MusicProcessor implements FileProcessor{
    @Override
    public boolean isSupported(String path) {
        return Optional.ofNullable(path)
                .filter(s -> s.contains("."))
                .map(s -> s.substring(s.lastIndexOf(".") + 1))
                .filter(s -> s.equals("mp3"))
                .isPresent();
    }

    @Override
    public String getDescription() {
        return """
                1. Gets name of a song.
                2. Gets duration of a song.
                3. Gets information about artist of a song.
                """;
    }

    @SneakyThrows
    @Override
    public void process(String path, int option){
        File file = new File(path);

        InputStream input = new FileInputStream(file);
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Mp3Parser parser = new Mp3Parser();
        ParseContext context = new ParseContext();

        parser.parse(input, handler, metadata, context);
        input.close();

        switch (option) {
            case (1):
                System.out.println("Title: " + metadata.get("title"));
                break;
            case (2):
                System.out.println("Duration: " + metadata.get("xmpDM:duration"));
                break;
            case (3):
                System.out.println("Artists: " + metadata.get("xmpDM:artist"));
                break;
        }
    }
}
