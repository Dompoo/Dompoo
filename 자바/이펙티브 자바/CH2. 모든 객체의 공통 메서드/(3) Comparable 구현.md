# Comparable 구현

- `Comparable`을 구현한다는 것은 해당 클래스의 인스턴스들에게 **자연적인 순서**가 있음을 뜻한다.
- 이런 경우 `Comparable`을 구현하여 `Arrays.sort()`등의 메서드에서 큰 도움을 받을 수 있으므로 꼭 오버라이딩하자.

## 일반적인 compareTo() 구현

```java
public class Member implements Comparable<Member> {
    private long id;
    private String name;
    private Team team;

    // ...
    
    @Override
    public int compareTo(Member o) {
        
        int result = Long.compare(this.getId(), o.getId());
        if (result != 0) return result;
        
        result = Objects.compare(this.getName(), o.getName(), String::compareTo);
        if (result != 0) return result;
        
        return Objects.compare(this.getTeam(), o.getTeam(), Comparator.comparingLong(Team::getId));
    }
}

```

## Comparator를 통해 compareTo() 구현

```java
public class Member implements Comparable<Member> {
    private static final Comparator<Member> COMPARATOR =
            Comparator.comparingLong((Member other) -> other.id)
                    .thenComparing(other -> other.name)
                    .thenComparing(other -> other.team.getId());

    private long id;
    private String name;
    private Team team;
    
    @Override
    public int compareTo(Member o) {
        return COMPARATOR.compare(this, o);
    }
}
```
