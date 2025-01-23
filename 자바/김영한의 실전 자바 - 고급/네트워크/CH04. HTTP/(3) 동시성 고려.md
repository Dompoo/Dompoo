# 동시성 고려

- 동시에 많은 요청을 처리하기 위해서는 멀티스레드로 서버를 설계하면 된다.
- main 스레드는 계속해서 별도의 스레드로 동작하는 세션을 생성하고, 각 세션이 `Socket`을 통해 응답을 처리하도록 한다.

## 세션

```java
public class HttpServerV2Session implements Runnable {
	
	private final Socket socket;
	
	public HttpServerV2Session(Socket socket) {
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
			String response = createResponseAsString();
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
	
	private static String createResponseAsString() {
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

- 기존의 주요 로직을 그대로 가져왔다.

## 서버

```java
public class HttpServerV2 {
	
	private final ExecutorService es = Executors.newFixedThreadPool(10);
	private final int port;
	
	public HttpServerV2(int port) {
		this.port = port;
	}
	
	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		log("서버 시작, port=" + port);
		
		while (true) {
			Socket socket = serverSocket.accept();
			es.submit(new HttpServerV2Session(socket));
		}
	}
}
```

- `ExecutorService`를 통해 스레드풀을 사용하였다.
