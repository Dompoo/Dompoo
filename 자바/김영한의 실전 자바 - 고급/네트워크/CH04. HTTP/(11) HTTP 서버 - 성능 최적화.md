# 성능 최적화

## 컨트롤러의 메서드 탐색 성능 최적화

```java
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
```

- 이 부분에서 O(컨트롤러 개수 * 메서드 개수) 만큼의 연산을 계속해서 수행한다.
- 더 최적화 할 수는 없을까?
- 아이디어는 미리 `Map`으로 해당 매핑 정보를 들고 있는 것이다.

```java
public class AnnotationReflectionServletV3 implements HttpServlet {
	
	private final Map<String, ControllerMethod> pathMap = new HashMap<>();
	
	public AnnotationReflectionServletV3(List<Object> controllers) {
		initPathMap(controllers);
	}
	
	private void initPathMap(List<Object> controllers) {
		for (Object controller : controllers) {
			Method[] methods = controller.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (!method.isAnnotationPresent(HttpMapping.class)) {
					continue;
				}
				String url = method.getAnnotation(HttpMapping.class).url();
				ControllerMethod controllerMethod = new ControllerMethod(controller, method);
				pathMap.put(url, controllerMethod);
			}
		}
	}
	
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String path = request.getPath();
		
		ControllerMethod controllerMethod = pathMap.get(path);
		if (controllerMethod == null) {
			throw new PageNotFoundException("request = " + path);
		}
		
		controllerMethod.invoke(request, response);
	}
	
	private static class ControllerMethod {
		
		private final Object controller;
		private final Method method;
		
		public ControllerMethod(Object controller, Method method) {
			this.controller = controller;
			this.method = method;
		}
		
		public void invoke(HttpRequest request, HttpResponse response) {
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
}
```

- 복잡해보이지만 결국 미리 init 하는 것이 중요하다.
- 덕분에 여러 요청이 와도 각 요청에 O(1) 시간만이 걸린다.

## 중복 매핑 문제

- 만약 같은 url을 받을 수 있는 엔드포인트가 2개 이상이라면 어떻게 할까?
- 무엇이 호출될지는 모르겠지는 둘 중에 하나만이 호출될 것이다. (반복문에서 먼저 찾은 것만이 실행)
- 이것을 검출하고 예외를 발생시켜주면 좋을 것 같다.
- 위에서 미리 init하는 부분에서 url 중복을 검사하며 저장하는 식으로 해결해보자.

```java
private void initPathMap(List<Object> controllers) {
	for (Object controller : controllers) {
		Method[] methods = controller.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (!method.isAnnotationPresent(HttpMapping.class)) {
				continue;
			}
			String url = method.getAnnotation(HttpMapping.class).url();
			if (pathMap.containsKey(url)) {
				throw new IllegalStateException("컨트롤러 엔드포인트의 url이 중복되었습니다.");
			}
			ControllerMethod controllerMethod = new ControllerMethod(controller, method);
			pathMap.put(url, controllerMethod);
		}
	}
}
```
