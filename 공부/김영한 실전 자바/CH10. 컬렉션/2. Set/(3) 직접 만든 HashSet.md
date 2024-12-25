```java
import java.util.Arrays;  
import java.util.LinkedList;  
  
public class MyHashSet {  
      
    private static final int DEFAULT_CAPACITY = 10;  
      
    private LinkedList<Integer>[] buckets;  
    private int size;  
    private final int capacity;  
      
    public MyHashSet() {  
       this.capacity = DEFAULT_CAPACITY;  
       initBuckets(capacity);  
    }  
      
    public MyHashSet(int capacity) {  
       this.capacity = capacity;  
       initBuckets(capacity);  
    }  
      
    private void initBuckets(int capacity) {  
       this.buckets = new LinkedList[capacity];  
       for (int i = 0; i < buckets.length; i++) {  
          buckets[i] = new LinkedList<>();  
       }  
    }  
      
    public boolean add(int value) {  
       LinkedList<Integer> bucket = getBucket(value);  
       if (bucket.contains(value)) {  
          return false;  
       } else {  
          size++;  
          return bucket.add(value);  
       }  
    }  
      
    public boolean contains(int value) {  
       LinkedList<Integer> bucket = getBucket(value);  
       return bucket.contains(value);  
    }  
      
    public boolean remove(int value) {  
       LinkedList<Integer> bucket = getBucket(value);  
       if (!bucket.contains(value)) {  
          return false;  
       } else {  
          size--;  
          return bucket.remove(Integer.valueOf(value));  
       }  
    }  
      
    public int getSize() {  
       return size;  
    }  
      
    @Override  
    public String toString() {  
       return "MyHashSet{" +  
             "buckets=" + Arrays.toString(buckets) +  
             ", size=" + size +  
             ", capacity=" + capacity +  
             '}';  
    }  
      
    private LinkedList<Integer> getBucket(int value) {  
       int hashIndex = hashIndex(value);  
       return buckets[hashIndex];  
    }  
      
    public int hashIndex(int value) {  
       return value % capacity;  
    }  
}
```
- 해시를 도입한 덕분에 모든 연산의 속도가 `O(1)`로 줄었다.
	- `Hash Collision`이 자주 발생하면 느려지겠지만, 거의 항상 한번에 찾는다.
- 문제는 현재 **숫자만 저장 가능**하다는 것이다.
	- 모든 자료형에 대해 적절한 해시값을 찾는 방법이 있어야 한다.
	- 이것이 `Object`의 `hashCode()` 메서드의 역할이다.
```java
import java.util.Arrays;  
import java.util.LinkedList;  
  
public class MyHashSet<T> {  
      
    private static final int DEFAULT_CAPACITY = 10;  
      
    private LinkedList<T>[] buckets;  
    private int size;  
    private final int capacity;  
      
    public MyHashSet() {  
       this.capacity = DEFAULT_CAPACITY;  
       initBuckets(capacity);  
    }  
      
    public MyHashSet(int capacity) {  
       this.capacity = capacity;  
       initBuckets(capacity);  
    }  
      
    private void initBuckets(int capacity) {  
       this.buckets = new LinkedList[capacity];  
       for (int i = 0; i < buckets.length; i++) {  
          buckets[i] = new LinkedList<>();  
       }  
    }  
      
    public boolean add(T value) {  
       LinkedList<T> bucket = getBucket(value);  
       if (bucket.contains(value)) {  
          return false;  
       } else {  
          size++;  
          return bucket.add(value);  
       }  
    }  
      
    public boolean contains(T value) {  
       LinkedList<T> bucket = getBucket(value);  
       return bucket.contains(value);  
    }  
      
    public boolean remove(T value) {  
       LinkedList<T> bucket = getBucket(value);  
       if (!bucket.contains(value)) {  
          return false;  
       } else {  
          size--;  
          return bucket.remove(value);  
       }  
    }  
      
    public int getSize() {  
       return size;  
    }  
      
    @Override  
    public String toString() {  
       return "MyHashSet{" +  
             "buckets=" + Arrays.toString(buckets) +  
             ", size=" + size +  
             ", capacity=" + capacity +  
             '}';  
    }  
      
    private LinkedList<T> getBucket(T value) {  
       return buckets[Math.abs(value.hashCode()) % capacity];  
    }  
}
```