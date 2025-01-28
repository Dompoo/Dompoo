# 리플렉션 도입

- 리플렉션을 통해 메서드명을 확인하여, 자동으로 URL 경로와 매칭해보자.
- 예를 들어 `/site1` URL로 들어온 요청은 `site1()` 메서드를 호출하는 식이다.
- 이러면 하나의 컨트롤러에 관련된 여러 메서드를 둘 수 있어서 더 관리하기 쉬워질 것이다.

## SiteController

```java
public class SiteControllerV6 {
	
	public void site1(HttpRequest request, HttpResponse response) {
		response.writeBody("<h1>site1</h1>");
	}
	
	public void site2(HttpRequest request, HttpResponse response) {
		response.writeBody("<h1>site2</h1>");
	}
}
```

## SearchController

```java
public class SearchControllerV6 {
	
	public void search(HttpRequest request, HttpResponse response) {
		String query = request.getParameter("q");
		response.writeBody("<h1>Search</h1>");
		response.writeBody("<ul>");
		response.writeBody("<li>query: " + query + "</li>");
		response.writeBody("</ul>");
	}
}
```

## ReflectionServlet

```java
public class ReflectionServlet implements HttpServlet {
	
	private final List<Object> controllers;
	
	public ReflectionServlet(List<Object> controllers) {
		this.controllers = controllers;
	}
	
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String path = request.getPath();
		
		for (Object controller : controllers) {
			Class<?> aClass = controller.getClass();
			Method[] methods = aClass.getDeclaredMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (path.equals("/" + methodName)) {
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

- 기존에는 하나의 서블릿이 하나의 URL과 매칭되었지만, 이번 서블릿은 하나의 서블릿이 모든 URL과 매칭된다.
- 즉, 이 `ReflectionServlet`을 `defaultServlet`으로 두면 된다.
  - 기존 : URL-Servlet = 1:1, Servlet-기능 = 1:1
  - 현재 : URL-Servlet = N:1, Servlet-Controller = 1:N, Controller-기능 = 1:N
- 단 아래 두가지 서블릿은 따로 추가해주어야 한다.
  - 홈 서블릿 : 메서드명이 비어있을 수 없으므로 추가한다.
  - 파비콘 서블릿 : 메서드명에 .이 들어갈 수 없으므로 추가한다.

## 서블릿 등록

```java
List<Object> controllers = List.of(new SiteControllerV6(), new SearchControllerV6());
ReflectionServlet reflectionServlet = new ReflectionServlet(controllers);
		
ServletManager servletManager = new ServletManager();
servletManager.setDefaultServlet(reflectionServlet);
servletManager.add("/", new HomeServlet());
servletManager.add("/favicon.ico", new NoOpServlet());
		
HttpServer server = new HttpServer(PORT, servletManager);
server.start();
```
