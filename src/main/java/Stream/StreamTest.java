package Stream;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) throws IOException {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6);
        Stream<Integer> stream = integerList.stream();

        Stream<Object> build = Stream.builder().add("a").add("c").add("a").build();


        Stream<String> lines = Files.lines(Paths.get("src/main/java/data.txt"), Charset.defaultCharset());
        Stream<String> lines2 = Files.lines(Paths.get("src/main/java/data.txt"), Charset.defaultCharset());

        System.out.println(lines2.collect(Collectors.toList()).toString());
        String collect = lines.collect(Collectors.joining(","));
        System.out.println(collect);


    }
}
