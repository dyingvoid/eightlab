package project.eightlab.fileProcessing;

import lombok.Cleanup;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TextProcessor implements FileProcessor {
    public void process(String path) throws IOException {
        List<String> data = readFile(path);
        long numberLines = countLines(data);
        Map<Character, Long> numberLetters = countLetters(data);
        Map<String, Long> numberWords = countWords(data);

        System.out.println("Number of lines: " + numberLines);

        System.out.println("Occurrence of each letter:");
        printMap(numberLetters);

        System.out.println("Occurence of each word:");
        printMap(numberWords);
    }

    private List<String> readFile(String path) throws IOException {
        @Cleanup BufferedReader reader = new BufferedReader(new FileReader(path));
        return reader.lines().toList();
    }

    private <K, V> void printMap(Map<K, V> map){
        map.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .forEach(System.out::println);
    }

    private long countLines(List<String> data){
        return data.size();
    }

    private Map<Character, Long> countLetters(List<String> data){
        return data
                .stream()
                .flatMapToInt(CharSequence::chars)
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    private Map<String, Long> countWords(List<String> data){
        return data
                .stream()
                .flatMap(line -> Stream.of(line.split(" ")))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
    }
}
