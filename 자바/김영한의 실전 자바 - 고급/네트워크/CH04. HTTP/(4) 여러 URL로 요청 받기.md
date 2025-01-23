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
