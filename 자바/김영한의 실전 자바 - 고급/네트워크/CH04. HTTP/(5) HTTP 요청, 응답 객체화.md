# HTTP 요청, 응답 객체화

- HTTP 요청과 응답은 시작라인/헤더라인/빈줄/바디 라는 공통된 형태를 지니고 있다.
- 이것을 객체로 만들어서 유지보수, 사용하기 쉽게 만들어보자.

## 요청 객체

```java
public class HttpRequest {
	
	private final Map<String, String> queryParams = new HashMap<>();
	private final Map<String, String> headers = new HashMap<>();
	
	private String method;
	private String path;
	
	public HttpRequest(BufferedReader reader) throws IOException {
		parseRequestLine(reader);
		parseHeaders(reader);
		// TODO : 메시지 바디 처리
	}
	
	private void parseRequestLine(BufferedReader reader) throws IOException {
		String requestLine = reader.readLine();
		String[] parts = requestLine.split(" ");
		
		this.method = parts[0];
		String[] pathParts = parts[1].split("\\?");
		this.path = pathParts[0];
		if (pathParts.length > 1) {
			parseQueryParams(pathParts[1]);
		}
	}
	
	private void parseQueryParams(String queryParamString) {
		for (String param : queryParamString.split("&")) {
			String[] data = param.split("=");
			queryParams.put(
					URLDecoder.decode(data[0], UTF_8),
					URLDecoder.decode(data[1], UTF_8)
			);
		}
	}
	
	private void parseHeaders(BufferedReader reader) throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			String[] headerParts = line.split(": ");
			headers.put(headerParts[0], headerParts[1]);
		}
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getParam(String name) {
		return queryParams.get(name);
	}
	
	public String getHeader(String name) {
		return headers.get(name);
	}
	
	@Override
	public String toString() {
		return "HttpRequest{" +
				"queryParams=" + queryParams +
				", headers=" + headers +
				", method='" + method + '\'' +
				", path='" + path + '\'' +
				'}';
	}
}
```

- 단순한 문자열 연산들이다.
- 이것을 통해 `Reader`만 넣어주면 원하는 데이터를 뽑아볼 수 있는 객체이다.
- 메시지 바디 부분 파싱은 필요해지면 추가하자.

## 응답 객체

```java
public class HttpResponse {
	
	private final PrintWriter writer;
	private final StringBuilder bodyBuilder = new StringBuilder();
	private int statusCode = 200;
	private String contentType = "text/html; chatset=UTF-8";
	
	public HttpResponse(PrintWriter writer) {
		this.writer = writer;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public void writeBody(String body) {
		bodyBuilder.append(body);
	}
	
	public void flush() {
		int contentLength = bodyBuilder.toString().getBytes(StandardCharsets.UTF_8).length;
		writer.println("HTTP/1.1 " + statusCode + " " + getReasonPhrase(statusCode));
		writer.println("Content-type: " + contentType);
		writer.println("content-Length: " + contentLength);
		writer.println();
		writer.println(bodyBuilder);
		writer.flush();
	}
	
	private String getReasonPhrase(int statusCode) {
		return switch (statusCode) {
			case 200 -> "OK";
			case 404 -> "NOT FOUND";
			case 500 -> "INTERNAL SERVER ERROR";
			default -> "UNKNOWN STATUS";
		};
	}
}
```

- 이 객체를 통해서 적절하게 메시지 바디를 설정하고, `flush()`하여 응답을 보낼 수 있다.
