package project.eightlab.fileProcessing;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DirectoryProcessor implements FileProcessor {
    @Override
    public boolean isSupported(String path) {
        return (new File(path)).isDirectory();
    }

    @Override
    public String getDescription() {
        return """
                1. List all catalogue's files.
                2. Show largest catalogue's file.
                """;
    }

    @Override
    public void process(String path, int option) {
        File file = new File(path);

        Map<String, Long> ls = Stream.of(file.listFiles())
                .filter(File::isFile)
                .collect(Collectors.toMap(File::getName, FileUtils::sizeOf));

        switch (option) {
            case (1):
                ls.entrySet().stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue() + " bytes")
                        .forEach(System.out::println);
                break;
            case (2):
                Optional<Map.Entry<String, Long>> filename = ls.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue());

                filename.ifPresent(pair ->
                        System.out.println(
                                "Largest file is: " + pair.getKey() +
                                        "with size of " + pair.getValue() + " bytes."
                        )
                );
                break;
        }
    }
}
