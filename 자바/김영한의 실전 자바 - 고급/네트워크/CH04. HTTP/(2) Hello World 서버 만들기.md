# Hello World 서버 만들기

- 기존 서버와는 다르게, 요청을 받자마자 응답을 바로 내려줄 수 있으므로 구조가 좀 더 간단하다.
- 요청이 오면 해당 요청을 출력하고, Hello World를 반환해보자.

## 코드

```java
public class HttpServerV1 {
	
	private final int port;
	
	public HttpServerV1(int port) {
		this.port = port;
	}
	
	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		log("서버 시작, port=" + port);
		
		while (true) {
			Socket socket = serverSocket.accept();
			processHttpRequest(socket);
		}
	}
	
	private void processHttpRequest(Socket socket) throws IOException {
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
	
	private String createResponseAsString() {
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

## 출력 결과

```text
23:25:58.131 [     main] 서버 시작, port=12345
23:25:59.875 [     main] === HTTP 요청 정보 ===
GET / HTTP/1.1
Host: localhost:12345
Connection: keep-alive
Cache-Control: max-age=0
sec-ch-ua: "Not A(Brand";v="8", "Chromium";v="132"
sec-ch-ua-mobile: ?0
sec-ch-ua-platform: "macOS"
DNT: 1
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Sec-Fetch-Site: none
Sec-Fetch-Mode: navigate
Sec-Fetch-User: ?1
Sec-Fetch-Dest: document
Accept-Encoding: gzip, deflate, br, zstd
Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7

23:25:59.876 [     main] === HTTP 응답 생성 시작 ===
23:25:59.877 [     main] === HTTP 응답 정보 ===
HTTP/1.1 200 OK
Content-Type: text/html
Content-Length: 21

<h1>Hello World!</h1>
```

- 적절하게 요청을 출력하고 응답을 출력한다.
- 브라우저에서 실행해보면 Hello World가 출력되는 것을 볼 수 있다.
- 하지만 현재는 한번에 하나의 요청만 처리할 수 있다. 동시성 문제를 해결해보자.
