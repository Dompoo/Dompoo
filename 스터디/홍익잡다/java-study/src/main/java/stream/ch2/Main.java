package stream.ch2;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Hello", "World", "Hello", "Dompoo");
        Stream<String> stream = list.stream();

        //스트림의 중간 연산 (스트림에서 벗어나지 않고 변형되거나 사용됨)
        Stream<dto> dtoStream = stream.map(dto::new);
        Stream<String> peek = stream.peek(System.out::println);
        Stream<String> stringStream = stream.filter(str -> str.length() < 5);
        Stream<String> distinct = stream.distinct();
        Stream<String> limit = stream.limit(5);

        //스트림의 끝 연산 (스트림을 벗어나게 됨)
        List<String> ls = stream.toList();
        Set<String> collect = stream.collect(Collectors.toSet());
        long count = stream.count();
        stream.forEach(System.out::println);
        Optional<String> reduce = stream.reduce((str1, str2) -> str1.concat(str2));
    }

    public static class dto {
        public String name;
        public int length;

        public dto(String str) {
            this.name = str;
            this.length = str.length();
        }
    }
}
