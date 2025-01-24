# 여러 URL로 요청 받기

- URL에 따라 응답을 다르게 하기 위해서는 세션 부분 코드를 수정하면 된다.
- `request` 값에 따라서 다른 응답 결과 문자열을 생성하여 반환하자.

```java
public class HttpServerV3Session implements Runnable {
	
	private final Socket socket;
	
	public HttpServerV3Session(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			processHttpRequest(socket);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void processHttpRequest(Socket socket) throws IOException {
		try (socket;
			 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			 PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, StandardCharsets.UTF_8)
		) {
			String requestString = getRequestAsString(reader);
			
			if (requestString.contains("/favicon.ico")) {
				return;
			}
			
			log("=== HTTP 요청 정보 ===");
			System.out.println(requestString);
			
			log("=== HTTP 응답 생성 시작 ===");
			String response = null;
			if (requestString.startsWith("GET /site1")) {
				response = createSite1AsString();
			} else if (requestString.startsWith("GET /site2")) {
				response = createSite2AsString();
			} else {
				response = createDefaultResponseAsString();
			}
			writer.println(response);
			writer.flush();
			
			log("=== HTTP 응답 정보 ===");
			System.out.println(response);
		}
	}
	
	private static String getRequestAsString(BufferedReader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.isEmpty()) {
				break;
			}
			sb.append(line).append("\n");
		}
		return sb.toString();
	}
	
	private static String createSite1AsString() {
		String body = "<h1>Hello World!</h1><br><h2>This is Site1<h2>";
		int length = body.getBytes(StandardCharsets.UTF_8).length;
		
		StringBuilder sb = new StringBuilder();
		// 시작 라인
		sb.append("HTTP/1.1 200 OK\r\n");
		// 헤더 라인
		sb.append("Content-Type: text/html\r\n");
		sb.append("Content-Length: ").append(length).append("\r\n");
		sb.append("\r\n"); // 헤더-바디 구분
		// 바디 라인
		sb.append(body);
		
		return sb.toString();
	}
	
	private static String createSite2AsString() {
		String body = "<h1>Hello World!</h1><br><h2>This is Site2<h2>";
		int length = body.getBytes(StandardCharsets.UTF_8).length;
		
		StringBuilder sb = new StringBuilder();
		// 시작 라인
		sb.append("HTTP/1.1 200 OK\r\n");
		// 헤더 라인
		sb.append("Content-Type: text/html\r\n");
		sb.append("Content-Length: ").append(length).append("\r\n");
		sb.append("\r\n"); // 헤더-바디 구분
		// 바디 라인
		sb.append(body);
		
		return sb.toString();
	}
	
	private static String createDefaultResponseAsString() {
		String body = "<h1>Hello World!</h1>";
		int length = body.getBytes(StandardCharsets.UTF_8).length;
		
		StringBuilder sb = new StringBuilder();
		// 시작 라인
		sb.append("HTTP/1.1 200 OK\r\n");
		// 헤더 라인
		sb.append("Content-Type: text/html\r\n");
		sb.append("Content-Length: ").append(length).append("\r\n");
		sb.append("\r\n"); // 헤더-바디 구분
		// 바디 라인
		sb.append(body);
		
		return sb.toString();
	}
}
```

## URL 인코딩

- `URL`로 여러 문자를 받다보면 한글도 받아보고 싶어진다.
- 하지만, `URL`로 `비ASCII` 문자를 받아보면, 잘못된 인코딩이 된 것처럼 글자가 깨진다.
- 이는 인터넷이 처음 설계되던 당시 대부분의 컴퓨터 시스템이 `ASCII`를 사용했기 때문에 인터넷의 설계도 그렇게 되었기 때문이다.
- 또한 현재는 호환성을 위해 바뀌지는 못하는 상황이다.

### 퍼센트 인코딩

- 그러면 깨져서 온 문자는 어떤 값일까?
- **퍼센트 인코딩**이 된 값이다.
  - 한글을 `UTF-8` 인코딩으로 표현하면 글자당 3byte
  - 따라서 '가'를 16진수 `UTF-8`로 표현하면 EA, BO, 80
  - 여기 앞에다가 %를 붙인 것이다.
- 결과 : `가` -> `%EA%B0%80`
- 억지스럽지만, 호환성을 최대한 늘리기 위한 결정이며, 성능이 조금 나빠지더라도 긴 내용은 body로 받으면 되기 때문에 큰 상관이 없다.

### 퍼센트 디코딩

- 그러면 우리 서버에서는 인코딩의 역순으로 URL에 한글을 받아보자.
- 귀찮게 우리가 할 필요없이, 클래스를 통해 할 수 있다.

```java
String decoded = URLDecoder.decode(message, UTF-8);
```
