# DataStream

- 자바의 데이터 바이트를 그대로 저장해버리는 보조 스트림 `DataStream`을 활용해보자.
- 귀찮은 변환 작업이 줄어들 것이다.

```java
public class DataMemberRepository implements MemberRepository {
	
	private static final String FILE_PATH = "temp/members-data.txt";
	
	@Override
	public void add(Member member) {
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_PATH, true))) {
			dos.writeUTF(member.getId());
			dos.writeUTF(member.getName());
			dos.writeInt(member.getAge());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Member> findAll() {
		List<Member> members = new ArrayList<>();
		
		try (DataInputStream dis = new DataInputStream(new FileInputStream(FILE_PATH))) {
			while (dis.available() > 0) {
				Member findMember = new Member(dis.readUTF(), dis.readUTF(), dis.readInt());
				members.add(findMember);
			}
		} catch (FileNotFoundException e) {
			return new ArrayList<>();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return members;
	}
}
```

- `DataStream` 덕분에 귀찮은 변환 작업 없이 **순서만 맞춰주면** 잘 읽어올 수 있었다.
  - 심지어 한줄 단위로 구분할 필요도 없다.
- 근데 생각해보면 문자열을 저장할 때 줄바꿈이나 구분자 없이 적절하게 데이터를 끊어서 읽어오는 것이 신기하다.
  - `writeUTF()`는 사실 UTF 방식으로 저장할 뿐 아니라, 앞에 2byte를 추가로 사용하여 문자열 길이를 같이 저장한다.
  - `3id1`, `5hello` 같은 느낌으로 저장되기 때문에 적절하게 문자열 부분만을 끊어서 읽을 수 있었다.
- readUTF, readUTF, readInt 순서대로 호출하면
  - 먼저 2바이트를 읽고 그 길이만큼 문자열을 읽어들인다.
  - 2바이트를 읽고 그 길이만큼 문자열을 읽어들인다.
  - 4바이트를 읽고 `Int`를 읽어들인다.
- 하지만 각 필드를 따로 분리하여 저장해야 한다. 한번에 쫙!하고 저장할 수는 없을까?
