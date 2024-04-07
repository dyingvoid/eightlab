package project.eightlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.eightlab.fileProcessing.*;

import java.io.IOException;

@SpringBootApplication
public class EightlabApplication implements CommandLineRunner {
    @Autowired
    public EightlabApplication(FileProcessorFactory factory) {
        this.factory = factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(EightlabApplication.class, args);
    }

    private final FileProcessorFactory factory;

    @Override
    public void run(String... args) throws IOException {
        String path = "C:\\Users\\Dying\\IdeaProjects\\eightlab\\src\\main\\resources\\file.txt";
        FileProcessor processor = this.factory.getProccessor(path);
        processor.process(path);
    }
}
