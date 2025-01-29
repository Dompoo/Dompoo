# 애노테이션 적용

- 메서드명을 통해 url을 매칭하지 않고, 애노테이션의 값을 통해서 매핑하도록 수정하자.

## 애노테이션

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface HttpMapping {
	String url();
}
```

## 서블릿

```java
public class AnnotationReflectionServlet implements HttpServlet {
	
	private final List<Object> controllers;
	
	public AnnotationReflectionServlet(List<Object> controllers) {
		this.controllers = controllers;
	}
	
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String path = request.getPath();
		
		for (Object controller : controllers) {
			Class<?> aClass = controller.getClass();
			Method[] methods = aClass.getDeclaredMethods();
			for (Method method : methods) {
				if (!method.isAnnotationPresent(HttpMapping.class)) {
					continue;
				}
				HttpMapping annotation = method.getAnnotation(HttpMapping.class);
				if (path.equals(annotation.url())) {
					invokeMethod(controller, method, request, response);
					return;
				}
			}
		}
		throw new PageNotFoundException("request = " + path);
	}
	
	private static void invokeMethod(Object controller, Method method, HttpRequest request, HttpResponse response) {
		try {
			method.invoke(controller, request, response);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
```

## 컨트롤러

```java
public class SearchControllerV7 {
	
	@HttpMapping(url = "/search")
	public void search(HttpRequest request, HttpResponse response) {
		String query = request.getParameter("q");
		response.writeBody("<h1>Search</h1>");
		response.writeBody("<ul>");
		response.writeBody("<li>query: " + query + "</li>");
		response.writeBody("</ul>");
	}
}
```

```java
public class SiteControllerV7 {
	
	@HttpMapping(url = "/")
	public void home(HttpRequest request, HttpResponse response) {
		response.writeBody("<h1>home</h1>");
		response.writeBody("<ul>");
		response.writeBody("<li><a href='/site1'>site1</a></li>");
		response.writeBody("<li><a href='/site2'>site2</a></li>");
		response.writeBody("<li><a href='/search?q=hello'>검색</a></li>");
		response.writeBody("</ul>");
	}
	
	@HttpMapping(url = "/favicon.ico")
	public void favicon(HttpRequest request, HttpResponse response) {
		// 아무것도 하지 않는다.
	}
	
	@HttpMapping(url = "/site1")
	public void site1(HttpRequest request, HttpResponse response) {
		response.writeBody("<h1>site1</h1>");
	}
	
	@HttpMapping(url = "/site2")
	public void site2(HttpRequest request, HttpResponse response) {
		response.writeBody("<h1>site2</h1>");
	}
}
```

- 이렇게 애노테이션을 통해서 하는 덕분에 메서드명으로 매핑하지 못했던 url도 매핑이 된다.
  - `/` 매핑
  - `/favicon.ico` 매핑

## 메인 메서드

```java
public class ServerMainV7 {
	
	private static final int PORT = 12345;
	
	public static void main(String[] args) throws IOException {
		List<Object> controllers = List.of(new SiteControllerV7(), new SearchControllerV7());
		AnnotationReflectionServlet reflectionServlet = new AnnotationReflectionServlet(controllers);
		
		ServletManager servletManager = new ServletManager();
		servletManager.setDefaultServlet(reflectionServlet);
		
		HttpServer server = new HttpServer(PORT, servletManager);
		server.start();
	}
}
```
