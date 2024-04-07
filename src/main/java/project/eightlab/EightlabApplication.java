package project.eightlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.eightlab.fileProcessing.FileProcessor;
import project.eightlab.fileProcessing.ImageProcessor;

import java.io.IOException;

@SpringBootApplication
public class EightlabApplication implements CommandLineRunner {
    @Autowired
    public EightlabApplication(ImageProcessor processor) {
        this.processor = processor;
    }

    public static void main(String[] args) {
        SpringApplication.run(EightlabApplication.class, args);
    }

    private final ImageProcessor processor;

    @Override
    public void run(String... args) throws IOException {
        String path = "C:\\Users\\Dying\\Downloads\\Canon_40D.jpg";

        processor.process(path);
    }
}
