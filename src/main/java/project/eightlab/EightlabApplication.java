package project.eightlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.eightlab.fileProcessing.*;

import java.io.File;
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
        if(args.length < 1)
            return;

        Scanner scanner = new Scanner(System.in);
        String path = args[0].replace('\\', '/');

        if(!new File(path).exists()){
            System.out.println("File does not exist.");
            return;
        }

        FileProcessor processor = this.factory.getProcessor(path);
        System.out.println(processor.getDescription() + "To exit input 228.");

        for(int i = scanner.nextInt(); i != 228;) {
            processor.process(path, i);
            i = scanner.nextInt();
        }
    }
}
