AppConfig에서,
1. @Bean memberService() -> new MemoryMemberRepository();
2. @Bean memberRepository() -> new MemoryMemberRepository();
* 객체가 두개 생성되니 싱글톤이 깨지는 것 아닌가? (결국 자바인데, new가 되어야 하는 것 아닌가?)
* 결론적으로는 같은 인스턴스가 사용된다.
* 이 비밀은 @Configuration에 있다.
# @Configuration
* 해당 어노테이션을 통해 Bean 등록된 클래스(내 경우 AppConfig)는 **CGLIB(Code Generator Library)를 통해 바이트코드 조작**을 당한다.
* 조작된 해당 Configuration 클래스는 Bean 객체들을 무조건 생성하여 등록하는 것이 아니라, **먼저 스프링 컨테이너에서 찾고**, 있으면 그대로 등록 / 없으면 생성해서 등록하도록 한다.
* 추가적으로 조작된 코드는 AppConfig@CGLIB 같은 인스턴스로 등록되는데, 이는 AppConfig의 자식 타입이므로, AppConfig로 빈 조회하는데에는 문제가 없다.
### 정리
* @Bean만 있어도 메서드를 통해 스프링 컨테이너에 등록되는 것은 동일하다.
* 애플리케이션 컨텍스트에 넘기는 클래스는 빈 등록이 되므로 AppConfig도 등록이 된다.
* 다만, 바이트코드 조작이 이루어지지 않으므로 같은 클래스가 여러개 생성될 수 있다.(싱글톤이 깨질 수 있다.)
* @Configuration을 붙이면 바이트코드 조작을 통하여 싱글톤을 지키며 빈 등록을 진행한다.