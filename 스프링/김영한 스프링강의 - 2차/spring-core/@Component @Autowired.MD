# @Configuration, @Bean
* 이 방법을 사용하여 빈 등록을 할 수 있었다.
* 하지만, 빈 등록하고 싶은 모든 객체를 수동으로 적어주어야 했기 때문에 프로젝트가 커지면 귀찮아질 수 있따.
* 또한 의존관계가 달라지면 수정이 필요하다.
* 다른 방법이 없을까?

# @ComponentScan, @Component, @Autowired
* @Configuration파일에 @ComponentScan를 붙여 컴포넌트 스캔을 사용할 수 있다.
* 컴포넌트 스캔을 사용하면 사용할 빈을 따로 적어줄 필요없이 바로 해당 클래스에 @Component를 적어주어 등록할 수 있다.
* 이런 @Component로 등록된 빈의 의존관계를 주입해주기 위해서는 @Autowired를 쓰면 된다.
### 추가 정보
* 컴포넌트 스캔의 기본 범위는 해당 @ComponentScan이 붙은 패키지와 그 하위 패키지이다.
* 컴포넌트 스캔의 범위를 따로 설정할 수 있지만, 관례는 Config 파일을 프로젝트 루트에 두어, 따로 설정하지 않고 기본 범위를 사용한다.
* 스프링 프로젝트를 생성하면 기본적으로 만들어지는 @SpringBootApplication 어노테이션에도 @ComponentScan이 붙어있다. 또한 이 클래스는 루트에 위치하므로, 따로 Config 파일을 만들지 않아도 알아서 모든 @Component 클래스가 스프링 빈에 등록된다.
* @Controller, @Service, @Repository, @Configuration에도 까보면 @Component가 붙어있다. -> 컴포넌트 스캔의 대상이 된다.
* @Controller는 추가적으로 스프링 MVC컨트롤러로 인식한다.
* @Repository는 추가적으로 스프링 데이터 접근 계층으로 인식하고, 데이터 계층 예외를 스프링 예외로 변환한다.
* @Configuration은 추가적으로 스프링 설정 정보로 인식하고, CGLIB을 통해 싱글톤 유지를 위한 추가 작업이 수행된다.

# 정리
* @SpringBootApplication 이 전체 프로젝트에 대하여 컴포넌트 스캔을 실시한다.
* @Controller, @Service, @Repository, @Configuration등이 붙은 클래스는 안에 들어있는 @Component에 의하여 컴포넌트 스캔의 대상이 되며, 스프링 빈에 등록된다.
* 해당 클래스들의 의존관계는 @Autowired에 의하여 자동으로 주입된다.