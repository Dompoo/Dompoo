# equals(), hashCode()

- `equals()`는 다음과 같은 상황에서는 오버라이딩하지 않는다.
  - 각 인스턴스가 본질적으로 고유할 때
  - 논리적 동치성을 검사할 일이 없을 때
  - 상위 클래스의 `eqauls()`가 딱 들어맞을 때
- 그 외의 경우에는 대칭성/추이성/일관성을 잘 고려하며 오버라이딩한다.
  - 다행히 여러 IDE가 이를 잘 고려하여 생성해준다.

```java
public class Member {
    private long id;
    private String name;
    private Team team;

    // ...
    
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Member member)) return false;
        
        return getId() == member.getId() 
            && Objects.equals(getName(), member.getName()) 
            && Objects.equals(getTeam(), member.getTeam());
    }
    
    @Override
    public int hashCode() {
        int result = Long.hashCode(getId());
        result = 31 * result + Objects.hashCode(getName());
        result = 31 * result + Objects.hashCode(getTeam());
        return result;
    }
}
```

- `equals()`를 오버라이딩하였다면 `hashCode()`도 오버라이딩해야 한다.
- 이는 `hashCode()`를 활용하는 컬렉션 등에서 문제가 발생하기 때문인데, 내부적으로 `equals()`가 아닌 `hashCode()`를 통해 같은지 확인하기 때문이다.
