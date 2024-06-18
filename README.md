# Spring-Study

2024/06/18 시작

## 목차

1. [Java](#java)
2. [Spring Core](#spring-core)
3. [Spring with Database](#spring-with-database)
4. [Spring Security](#spring-security)
5. [Exception](#exception)
6. [Testing](#testing)

## Java

Java 관련 주제를 다룹니다.

- **함수형 인터페이스, 람다 표현식(익명함수)**: Java에서의 함수형 프로그래밍이란 무엇인가?
- **스트림 API**: 데이터 컬렉션 처리는 어떻게 하는가?
- **Gradle**: 빌드 자동화 도구란 무엇인가? 어떻게 사용되고 있는가?

## Spring Core

Spring의 핵심 기능에 대해 알아봅니다.

- **IoC 컨테이너, DI란?**: 객체의 생성과 생명주기 관리를 컨테이너가 담당한다는 것은 무엇인가?
- **Bean 설정 및 생명주기**: Spring에서의 Bean객체란 무엇인가?, 생명주기는 어떻게 되는가?
- **AOP란? Aspect, Advice, Pointcut, JoinPoint**: 관점 지향 프로그래밍이란 무엇인가? 어떻게 적용되는가?

## Spring with Database

데이터베이스와의 연동을 다룹니다.

- **Transaction, Spring Data JPA**: 데이터베이스 트랜잭션 관리와 Spring Data JPA 사용법, 이를 통해 데이터 일관성과 무결성을 유지하는 방법은?
- **QueryDSL**: 타입 안전한 쿼리를 작성하는 방법, 적용법

## Spring Security

보안 관련 내용을 다룹니다.

- **아키텍쳐**: Spring Security의 기본 구조와 원리를 학습, 인증과 권한 부여 과정을 이해
- **적용**: 실제 애플리케이션에 이를 어떻게 적용시킬 것인가?
- **CSRF, CORS**: CSRF(크로스 사이트 요청 위조)와 CORS(크로스 오리진 리소스 공유)는 무엇인가? Spring Security에서는 어떻게 적용되는가?
- **OAuth2.0, JWT**: OAuth2.0 프레임워크와 JWT(JSON Web Tokens)는 무엇인가? Spring에서 사용방법은?

## Exception

Spring MVC에서의 예외 처리 방법 - `@ExceptionHandler`와 `@ControllerAdvice`를 사용하여 애플리케이션 전반에 걸친 예외 처리 전략을 구현하는 방법을 학습

## Testing

테스트 주도 개발(TDD)은 무엇인가? Java에서 JUnit과 assertJ를 사용한 테스트 코드 작성 방법은? 좋은 테스트 코드란 무엇이고 어떻게 작성해야 하는가?