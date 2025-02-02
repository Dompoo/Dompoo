# @Conditional

- 특정 조건일 때만 특정 빈들을 등록해서 사용하도록 도와주는 기능이다.
- 스프링부트 자동 구성에서 자주 사용되는 기능이다.
- 이 기능을 사용하기 위해서는 `Condition` 인터페이스를 구현해야 한다.

```java

@Slf4j
public class MemoryCondition implements Condition {
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// 자바 시스템 속성에서 -Dmemory=on
		String memory = context.getEnvironment().getProperty("memory");
		log.info("memory={}", memory);
		return "on".equals(memory);
	}
}
```

- 메모리 속성이 on인 경우에만 동작하는 `Condition`이다.
- 참고로 스프링이 외부 설정을 `Environmet`로 추상화했기 때문에 다양한 외부 설정을 간단하게 읽어들일 수 있다.
- 이 컨디션에만 등록되는 구성을 작성해보자.

```java

@Configuration
@Conditional(MemoryCondition.class)
public class MemoryConfig {
	
	@Bean
	public MemoryController memoryController() {
		return new MemoryController(memoryFinder());
	}
	
	@Bean
	public MemoryFinder memoryFinder() {
		return new MemoryFinder();
	}
}
```

- 이렇게 하면 이 구성 파일의 빈들은 조건을 만족하는 경우에만 등록된다.
    - `-Dmemory=on` 일 경우에만 `MemoryController`, `MemoryFinder` 두 빈이 등록된다.

## 스프링부트의 @ConditionalOnXxx

```java

@Configuration
@ConditionalOnProperty(name = "memory", havingValue = "on")
public class MemoryConfig {
	
	@Bean
	public MemoryController memoryController() {
		return new MemoryController(memoryFinder());
	}
	
	@Bean
	public MemoryFinder memoryFinder() {
		return new MemoryFinder();
	}
}
```

- 이런 애노테이션을 통해 굳이 `Condition` 구현체를 작성하지 않아도 간단하게 `@Conditional`을 적용할 수 있다.
- 내부 구현은 별 것 없다. 스프링이 사용할만한 `Condition` 구현체를 미리 만들어두고, 이를 사용하는 애노테이션도 작성해놓은 것이다.
- 다른 애노테이션은 대표적으로 이런 것들이 있다.
    - `@ConditionalOnClass` : 클래스가 있는 경우 동작
    - `@ConditionalOnBean` : 빈이 있는 경우 동작
    - `@ConditionalOnProperty` : 환경 정보가 있는 경우 동작
    - `@ConditionalOnWebApplication` : 웹 애플리케이션인 경우 동작
- 스프링부트의 자동 구성에서는 `@ConditionalOnMissingBean`이 자주 사용된다.
    - 사용자가 직접 빈을 등록하지 않았을 경우에만 자동 구성하도록 하기 위함이다.
