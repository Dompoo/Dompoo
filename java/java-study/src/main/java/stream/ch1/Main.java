package stream.ch1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        //컬렉션 -> 스트림 : Collection에 정의된 stream 메서드
        List<Integer> list = Arrays.asList(1, 2, 3);
        Stream<Integer> s1 = list.stream();

        //배열 -> 스트림 : Arrays의 스태틱 메서드 stream
        Integer[] array = new Integer[]{1, 2, 3, 4};
        Stream<Integer> s2 = Arrays.stream(array);
    }
}
