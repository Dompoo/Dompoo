# JAR 빌드와 배포

- 내장 톰캣 덕분에 간편하게 `main()` 메서드를 실행하면 된다.

## Jar 형식으로 빌드

- 먼저 jar 형식으로 빌드해보자.
    - 이때, MAINFEST 파일에 `main()` 메서드가 들어있는 클래스를 지정해주어야 한다.
- 하지만, 해당 jar 파일을 실행해도 서버가 띄워지지 않는다.
- 이는 라이브러리 코드가 하나도 없기 때문이다.
    - 우리가 만든 소스코드만이 들어가 있다.
- war 파일에서는 라이브러리가 같이 포함되어 있었지만, jar 파일에서는 라이브러리가 포함되지 않는다.
- 이는 jar파일이 jar파일을 포함할 수 없기 때문이다.

## FatJar

- 이를 해결하는 방법은 jar안에 라이브러리 jar를 넣는 것이 아니라, 라이브러리 class 파일들을 넣는 것이다.
- 즉, 내 소스 코드의 class 파일과 라이브러리의 class 파일들이 혼재된 뚱뚱한 jar 파일이 생성된다.

```text
tasks.register('buildFatJar', Jar) {
    manifest {
        attributes 'Main-Class': 'hello.embed.EmbedTomcatSpringMain'
    }
    duplicatesStrategy = DuplicatesStrategy.WARN
    from { configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) } }
    with jar
}
```

- 이런 gradle task를 통해서 jar 파일을 생성할 수 있다.
- 이 jar 파일을 실행하면 드디어 서버를 실행할 수 있다.

## War와 FatJar

- WAR로 배포할 때에는 외부 서버를 설치해야 하는 등 설정이 귀찮았다.
- 외부 서버 대신에 서버를 라이브러리로 갖는 내장 서버 방식을 통해 `main()` 메서드 실행만으로 서버를 띄울 수 있었다.
- 하지만, jar 파일은 기본적으로 외부 라이브러리 jar 파일을 포함할 수 없었다.
- 따라서, 외부 라이브러리 jar 파일을 다 class 파일로 풀어서 전체를 다시 jar 파일로 빌드하였다. : **FatJar**

### FatJar 단점

- 의존성을 파악하기 어렵다. : 다 class 파일로 풀려있기 때문이다.
- 파일명 중복을 해결할 수 없다. : 클래스명, 리소스명이 동일한 경우 라이브러리가 다르더라도 같이 사용할 수 없다.
