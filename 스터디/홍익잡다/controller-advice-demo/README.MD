### Spring 에서는 예외를 어떻게 처리하는가?
* 내 로직 내부에서 예외가 발생하더라도 애플리케이션이 종료되지 않는게 너무 신기하다.
* 이것은 DispatcherServlet 이 대부분의 예외를 잡아 처리하기 때문이다.
* 디버그 모드를 통해 실제 어떻게 작동하고 있는지 확인해볼 수 있다.
```java
...
} catch (InvocationTargetException var9) {
	InvocationTargetException ex = var9;
	Throwable targetException = ex.getCause();
	if(targetException instanceof RuntimeException runtimeException) {
		throw runtimeException;
	}
}
...
```

```java
...
} catch (Exception var20) {
	Exception ex = var20;
	dispatchException = ex;
}
...
```

### 우리가 커스터마이징하고 싶다면?
* ControllerAdvice를 통해 가능하다.
* ControllerAdvice 내에 ExceptionHandler에 원하는 Exception 타입을 넣고 핸들하면 된다!

### ControllerAdvice는 어떻게 작동하는가?
* Advice라길래 AOP를 곧바로 떠올렸다.
* 그렇다. ControllerAdvice는 AOP의 또다른 고마운 결과물이다.
* 자세한 처리 과정은 https://mangkyu.tistory.com/246 에 상세히 서술되어 있다.
* https://reflectoring.io/spring-boot-exception-handling/#controlleradvice 에는 다른 예외처리 방법도 서술되어 있다.