package project.eightlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.eightlab.fileProcessing.*;

import java.io.IOException;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        String path = "C:\\Users\\Dying\\IdeaProjects\\eightlab\\src\\main\\resources\\file.txt";
        FileProcessor processor = this.factory.getProccessor(path);

        System.out.println(processor.getDescription() + "To exit input 228.");

        for(int i = scanner.nextInt(); i != 228;) {
            processor.process(path, i);
            i = scanner.nextInt();
        }
    }
}
