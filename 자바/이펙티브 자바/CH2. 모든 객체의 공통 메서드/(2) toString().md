# toString()

- `toString()`의 기본 구현은 객체의 정보를 알기 어렵게 되어있다.
- `toString()`을 오버라이딩하면 객체에 대한 정보를 잘 나타낼 수 있다.
  - 이를 통해 사용하기 편하고, 디버깅하기 좋은 객체가 만들어진다.

```java
public class Member {
    private long id;
    private String name;
    private Team team;
    
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team=" + team +
                '}';
    }
}
```
