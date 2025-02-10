import com.google.common.base.Objects;
import com.google.common.base.*;
import com.google.common.collect.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GuavaLearnTest {
    
    @Test
    void 컬렉션_초기화() {
        HashMap<String, String> hashMap = Maps.newHashMap();
        ArrayList<String> list = Lists.newArrayList();
        HashSet<String> set = Sets.newHashSet();
        
        Assertions.assertThat(hashMap).isNotNull();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(set).isNotNull();
    }
    
    @Test
    void 불변_컬렉션() {
        ImmutableList<String> immutableList = ImmutableList.of("hello", "world");
        // 더 많다.
        
        Assertions.assertThatThrownBy(() -> immutableList.add("haha"))
                .isExactlyInstanceOf(UnsupportedOperationException.class);
    }
    
    @Test
    void 함수형_스타일_프로그래밍() {
        List<String> list = Lists.newArrayList(null, "hello", "world", null, "!");
        Collection<String> result = Collections2.filter(list, Predicates.notNull());
        
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result).doesNotContainNull();
    }
    
    @Test
    void 멀티맵() {
        Multimap<String, String> multimap = HashMultimap.create();
        multimap.put("dompoo", "First");
        multimap.put("dompoo", "Second");
        multimap.put("dompoo", "Last");
        
        Collection<String> result = multimap.get("dompoo");
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result).containsExactlyInAnyOrder("First", "Second", "Last");
    }
    
    @Test
    void 바이맵() {
        HashBiMap<String, Integer> biMap = HashBiMap.create();
        biMap.put("index1", 10);
        biMap.put("index2", 20);
        biMap.put("index3", 30);
        
        Assertions.assertThatThrownBy(() -> biMap.put("index4", 30))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        Assertions.assertThat(biMap.get("index1")).isEqualTo(10);
        Assertions.assertThat(biMap.inverse().get(20)).isEqualTo("index2");
    }
    
    @Test
    void 멀티셋() {
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("hello");
        multiset.add("hello");
        multiset.add("world");
        
        Assertions.assertThat(multiset.count("hello")).isEqualTo(2);
        Assertions.assertThat(multiset.count("world")).isEqualTo(1);
    }
    
    @Test
    void 쉬운_해쉬_코드() {
        class HashCodeDemo {
            int foo;
            String bar;
            
            @Override
            public int hashCode() {
                return Objects.hashCode(foo, bar);
            }
        }
    }
    
    @Test
    void 쉬운_비교자() {
        class ComparableDemo implements Comparable<ComparableDemo> {
            int foo;
            String bar;
            
            @Override
            public int compareTo(ComparableDemo o) {
                return ComparisonChain.start()
                        .compare(foo, o.foo)
                        .compare(bar, o.bar)
                        .result();
            }
        }
    }
    
    @Test
    void 쉬운_검증() {
        class ValidationDemo {
            final int foo;
            final String bar;
            
            public ValidationDemo(int foo, String bar) {
                Preconditions.checkArgument(foo > 0, "foo는 0 이상이어야 합니다.");
                Preconditions.checkNotNull(bar, "bar는 null이면 안됩니다.");
                Preconditions.checkArgument(!bar.isBlank(), "bar는 비어있으면 안됩니다.");
                this.foo = foo;
                this.bar = bar;
            }
        }
    }
    
    @Test
    void 문자열_결합() {
        Joiner joiner = Joiner.on(",").skipNulls();
        String result = joiner.join("foo", null, "bar");
        
        Assertions.assertThat(result).isEqualTo("foo,bar");
    }
    
    @Test
    void 문자열_분할() {
        Splitter splitter = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings();
        Iterable<String> parts = splitter.split("foo,  bar,,   baz");
        
        Assertions.assertThat(parts).hasSize(3);
        Assertions.assertThat(parts).containsExactly("foo", "bar", "baz");
    }
    
    @Test
    void 명명규칙_변환() {
        String converted = CaseFormat.LOWER_UNDERSCORE
                .to(CaseFormat.LOWER_CAMEL, "some_property_name");
        
        Assertions.assertThat(converted).isEqualTo("somePropertyName");
    }
    
    @Test
    void 범위_처리() {
        Range<Integer> range = Range.closed(1, 10);
        
        Assertions.assertThat(range.contains(5)).isTrue();
        Assertions.assertThat(range.lowerEndpoint()).isEqualTo(1);
        
        ContiguousSet<Integer> numbers = ContiguousSet.create(range, DiscreteDomain.integers());
        Assertions.assertThat(numbers).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }
}
