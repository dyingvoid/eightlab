package project.eightlab.fileProcessing;

import lombok.Cleanup;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TextProcessor implements FileProcessor {
    @Override
    public boolean isSupported(String path) {
        return Optional.ofNullable(path)
                .filter(s -> s.contains("."))
                .map(s -> s.substring(path.lastIndexOf(".") + 1))
                .filter(s -> s.equals("txt"))
                .isPresent();
    }

    @Override
    public String getDescription() {
        return """
                1. Counts all lines in a file.
                2. Counts all letters in a file.
                3. Counts all words in a file.
                """;
    }

    public void process(String path, int option) throws IOException {
        List<String> data = readFile(path);

        switch (option) {
            case (1):
                long numberLines = countLines(data);
                System.out.println(numberLines);
                break;
            case (2):
                Map<Character, Long> numberLetters = countLetters(data);
                printMap(numberLetters);
                break;
            case (3):
                Map<String, Long> numberWords = countWords(data);
                printMap(numberWords);
                break;
        }
    }

    private List<String> readFile(String path) throws IOException {
        @Cleanup BufferedReader reader = new BufferedReader(new FileReader(path));
        return reader.lines().toList();
    }

    private <K, V> void printMap(Map<K, V> map) {
        map.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .forEach(System.out::println);
    }

    private long countLines(List<String> data) {
        return data.size();
    }

    private Map<Character, Long> countLetters(List<String> data) {
        return data
                .stream()
                .flatMapToInt(CharSequence::chars)
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    private Map<String, Long> countWords(List<String> data) {
        return data
                .stream()
                .flatMap(line -> Stream.of(line.split(" ")))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
    }
}
