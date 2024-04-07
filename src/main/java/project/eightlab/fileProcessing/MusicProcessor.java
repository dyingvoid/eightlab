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

@Component
public class MusicProcessor implements FileProcessor{
    @SneakyThrows
    @Override
    public void process(String path) throws IOException {
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
        System.out.println("Composer : "+metadata.get("xmpDM:composer"));
        System.out.println("Genre : "+metadata.get("xmpDM:genre"));
        System.out.println("Album : "+metadata.get("xmpDM:album"));
    }
}
