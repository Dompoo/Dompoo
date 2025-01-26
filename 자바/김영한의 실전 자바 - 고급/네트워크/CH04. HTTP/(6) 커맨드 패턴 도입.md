# 커맨드 패턴 도입

- 현재 코드의 문제점은 **서버 코드와 애플리케이션 코드가 분리가 안되어 있다**는 점이다.
- 서버 코드는 정확하게 분리하고, 이 코드를 활용하여 원하는 애플리케이션을 개발할 수 있도록 분리해보자.
- 그러기 위해서는 우선 커맨트 패턴을 통해서 URL에 따른 응답을 분리해보자.

## 서블릿 인터페이스

```java
public interface HttpServlet {
	
	void service(HttpRequest request, HttpResponse response) throws IOException;
}
```

- 이 인터페이스를 구현하여 서비스를 위한 코드를 작성하면 된다.

## 서블릿 구현체

```java
public class HomeServlet implements HttpServlet {
	
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		response.writeBody("<h1>home</h1>");
		response.writeBody("<ul>");
		response.writeBody("<li><a href='/site1'>site1</a></li>");
		response.writeBody("<li><a href='/site2'>site2</a></li>");
		response.writeBody("<li><a href='/search?q=hello'>검색</a></li>");
		response.writeBody("</ul>");
	}
}
```

```java
public class NotFoundServlet implements HttpServlet {
	
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		response.setStatusCode(404);
		response.writeBody("<h1>404 페이지를 찾을 수 없습니다.</h1>");
	}
}
```

## 서블릿 매니저

```java
public class ServletManager {
	
	private final Map<String, HttpServlet> servletMap = new HashMap<>();
	private HttpServlet defaultServlet;
	private HttpServlet notFoundErrorServlet = new NotFoundServlet();
	private HttpServlet internalErrorServlet = new InternalErrorServlet();
	
	public ServletManager() {
	}
	
	public void add(String path, HttpServlet servlet) {
		servletMap.put(path, servlet);
	}
	
	public void setDefaultServlet(HttpServlet defaultServlet) {
		this.defaultServlet = defaultServlet;
	}
	
	public void setNotFoundErrorServlet(HttpServlet notFoundErrorServlet) {
		this.notFoundErrorServlet = notFoundErrorServlet;
	}
	
	public void setInternalErrorServlet(HttpServlet internalErrorServlet) {
		this.internalErrorServlet = internalErrorServlet;
	}
	
	public void execute(HttpRequest request, HttpResponse response) throws IOException {
		try {
			HttpServlet servlet = servletMap.getOrDefault(request.getPath(), defaultServlet);
			if (servlet == null) {
				throw new PageNotFoundException("request url = " + request.getPath());
			}
			servlet.service(request, response);
		} catch (PageNotFoundException e) {
			e.printStackTrace();
			notFoundErrorServlet.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			internalErrorServlet.service(request, response);
		}
	}
}
```

- 이런 여러 커맨드들을 저장하고, 요청이 들어왔을 때 찾아서 실행하는 역할을 수행하는 매니저이다.
- 만약 서블릿을 찾지 못했다면 `PageNotFound` 서블릿을 실행하고, 문제가 생겼다면 `InternalError` 서블릿을 실행한다.

## HttpRequestHandler

```java
public class HttpRequestHandler implements Runnable {
    
    private final Socket socket;
    private final ServletManager servletManager;
    
    public HttpRequestHandler(Socket socket, ServletManager servletManager) {
        this.socket = socket;
        this.servletManager = servletManager;
    }
    
    @Override
    public void run() {
        try {
            process();
        } catch (Exception e) {
            log(e);
        }
    }

    private void process() throws IOException {
        try (
                socket;
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, UTF_8)
        ) {
            HttpRequest request = new HttpRequest(reader);
            HttpResponse response = new HttpResponse(writer);
            
            log("HTTP 요청 정보" + request);
            
            servletManager.execute(request, response);
            response.flush();
            
            log("HTTP 응답 완료");
        }
    }
}
```

- 모든 서비스 관련 코드가 빠지고 ServletManger를 통해 비즈니스 로직을 실행한다.
- 덕분에 코드도 매우 짧아지고 읽기 쉬워졌다.

## Main

- ServletManager의 모든 URL 매핑 정보를 넣어줄 곳이 필요하다.
- Main에서 ServletManager를 생성하고 세팅한 후에 서버에게 넘겨주도록 작성해보자.

```java
public class ServerMainV5 {
	
	private static final int PORT = 12345;
	
	public static void main(String[] args) throws IOException {
		ServletManager servletManager = new ServletManager();
		servletManager.add("/", new HomeServlet());
		servletManager.add("/site1", new Site1Servlet());
		servletManager.add("/site2", new Site2Servlet());
		servletManager.add("/search", new SearchServlet());
		servletManager.add("/favicon.ico", new NoOpServlet());
		
		HttpServer server = new HttpServer(PORT, servletManager);
		server.start();
	}
}
```

## 결론

- 이제 서버와 관련된 코드는 공통화되었고, 우리는 서블릿을 작성한 후에 `Main`에서 이를 주입해주기만 하면 된다.
- 덕분에 `ServletManager`, `HttpServer`, `HttpRequestHandler` 등의 코드를 전혀 수정할 필요가 없어졌다.
