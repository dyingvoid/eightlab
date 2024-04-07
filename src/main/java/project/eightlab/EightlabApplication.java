package project.eightlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.eightlab.fileProcessing.FileProcessor;
import project.eightlab.fileProcessing.ImageProcessor;
import project.eightlab.fileProcessing.MusicProcessor;

import java.io.IOException;

@SpringBootApplication
public class EightlabApplication implements CommandLineRunner {
    @Autowired
    public EightlabApplication(MusicProcessor processor) {
        this.processor = processor;
    }

    public static void main(String[] args) {
        SpringApplication.run(EightlabApplication.class, args);
    }

    private final MusicProcessor processor;

    @Override
    public void run(String... args) throws IOException {
        String path = "C:\\Users\\Dying\\Downloads\\file_example_MP3_700KB.mp3";

        processor.process(path);
    }
}
