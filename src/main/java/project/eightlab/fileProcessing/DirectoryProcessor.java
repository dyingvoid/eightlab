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
    public void process(String path) {
        File file = new File(path);

        if (!file.isDirectory())
            return;

        Map<String, Long> ls = Stream.of(file.listFiles())
                .filter(File::isFile)
                .collect(Collectors.toMap(File::getName, FileUtils::sizeOf));

        Optional<Map.Entry<String, Long>> filename = ls.entrySet().stream().max(Map.Entry.comparingByValue());

        ls.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue() + " bytes")
                .forEach(System.out::println);

        filename.ifPresent(pair ->
                System.out.println(
                        "Largest file is: " + pair.getKey() +
                                "with size of " + pair.getValue() + " bytes."
                )
        );
    }
}
