# 스프링 MVC 서블릿 컨테이너 초기화 지원

- 현재 코드는 다음과 같은 단계로 나뉜다.
    - `ServletContainerInitialzer`를 구현하여 서블릿 컨테이너 초기화 코드 작성
    - `@HandlesTypes`을 통해 인터페이스 구현체들을 set으로 받기
    - 해당 구현체들을 리플렉션으로 생성하여 등록
    - 구현체들 내부에서는 스프링 컨테이너 생성 / 디스패쳐 서블릿 등록을 한다.
- 이런 코드는 상당히 복잡하기 때문에, 이런 초기화 작업을 미리 만들어져있다.
- `WebApplicationInitializer`를 구현하기만 하면 된다.
  - 마법같은 일은 아니고, 스프링이 이미 만들어둔 `ServletContainerInitializer` 구현체에서 해당 인터페이스의 구현체들을 받는다.

```java
public class AppInitV3SpringMvc implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("AppInitV3Spring.onStartup");
		
		// 스프링 컨테이너 생성
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(HelloConfig.class);
		
		// 스프링 MVC 디스패쳐 서블릿 생성, 스프링 컨테이너 연결
		DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
		
		// 디스패쳐 서블릿을 서블릿 컨테이너에 등록
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcherV3", dispatcherServlet);
		servlet.addMapping("/");
	}
}
```
