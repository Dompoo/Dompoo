# 동적바인딩

- 현재 서버에 또 문제점이 있다.
- 바로 사용하지 않는 `HttpRequest`, `HttpResponse`가 존재해도 무조건 이를 메서드에 적어주어야 한다는 것이다.
  - 이는 서블릿에서 `HttpRequest`, `HttpResponse`를 컨트롤러의 코드를 실행할 때 무조건 넘겨주기 때문이다.
- 리플렉션을 통해 해당 파라미터가 존재하는 경우에만 넘겨주도록 수정해보자.

```java
public class AnnotationReflectionServletV2 implements HttpServlet {
	
	private final List<Object> controllers;
	
	public AnnotationReflectionServletV2(List<Object> controllers) {
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
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] args = new Object[parameterTypes.length];
		
		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i] == HttpRequest.class) {
				args[i] = request;
			} else if (parameterTypes[i] == HttpResponse.class) {
				args[i] = response;
			} else {
				throw new IllegalArgumentException("지원되지 않는 파라미터 타입입니다. : " + parameterTypes[i]);
			}
		}
		
		try {
			method.invoke(controller, args);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
```

- 동적으로 파라미터 타입을 체크하여, 각 타입에 맞는 것들만 넘겨주면서 메서드를 호출한다.
