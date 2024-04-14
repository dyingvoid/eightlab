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
import java.io.IOException;
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
    public void process(String path, int option) throws IOException {
        File file = new File(path);

        InputStream input = new FileInputStream(file);
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Mp3Parser parser = new Mp3Parser();
        ParseContext context = new ParseContext();

        parser.parse(input, handler, metadata, context);
        input.close();

        String[] metadataNames = metadata.names();

        for(String name : metadataNames){
            System.out.println(name + ": " + metadata.get(name));
        }

        System.out.println("----------------------------------------------");
        System.out.println("Title: " + metadata.get("title"));
        System.out.println("Artists: " + metadata.get("xmpDM:artist"));
        System.out.println("Composer : " + metadata.get("xmpDM:composer"));
        System.out.println("Genre : " + metadata.get("xmpDM:genre"));
        System.out.println("Album : " + metadata.get("xmpDM:album"));
    }
}
