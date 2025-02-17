# ObjectStream

## 객체 직렬화

- **객체 직렬화**는 객체를 통째로 바이트 스트림으로 변환하여 저장하거나 전송할 수 있도록 하는 기술이다.
- 나중에 이렇게 저장하거나 송신한 데이터를 **객체 역직렬화**를 통하여 객체로 복원할 수 있다.
- 직렬화를 사용하려면 `Serializable` 인터페이스를 구현해야 한다.
  - 구현할 기능은 없고 직렬화 가능하다는 객체라는 것을 표시만 하는 것이다.
  - 이런 것을 **마커 인터페이스**라고 부른다.

### ObjectStream 사용

```java
public class ObjectMemberRepository implements MemberRepository {

	private static final String FILE_PATH = "temp/members-obj.dat";

	@Override
	public void add(Member member) {
		List<Member> members = findAll();
		members.add(member);

		// 객체 하나당 하나의 파일이기 때문에 전체 컬렉션을 저장하고 조회한다.
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
			oos.writeObject(members);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Member> findAll() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
			Object findObject = ois.readObject();
			return (List<Member>) findObject;
		} catch (FileNotFoundException e) {
			return new ArrayList<>();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
```

- 정말 간단하게 정말 객체 통째로 저장하고 조회한다.
  - 이 저장한 값에는 데이터 값, 자료형 등등의 내용이 같이 저장된다.
- 단, 객체 하나당 하나의 파일을 사용하므로 멤버 하나만 저장하는 것이 아니라, 컬렉션 자체를 저장하고 조회해야 한다.
- 사실 이 기술은 여러 대안 기술이 등장하며 잘 사용하지 않는 방법이다.

### 저장 결과

```txt
�� sr java.util.ArrayListx����a� I sizexp   w   sr io.member.Member��c���D L aget Ljava/lang/Integer;L idt Ljava/lang/String;L nameq ~ xpsr java.lang.Integer⠤���8 I valuexr java.lang.Number������  xp   t 1t 3x
```
