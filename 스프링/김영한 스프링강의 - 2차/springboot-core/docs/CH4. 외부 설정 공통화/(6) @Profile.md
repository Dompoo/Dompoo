# @Profile

- 프로필에 따라 다른 설정 값이 아니라, 프로필에 따라 아예 다른 빈을 등록하는 기능이다.

```java

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final PayClient payClient;
    
    public void order(int money) {
        payClient.pay(money);
    }
}
```

- `PayClient`는 인터페이스고 프로필에 따라 두 구현체를 받아서 사용해야 하는 상황이다.
- 이때 구성 파일에서 `@Profile`을 사용할 수 있다.

```java

@Slf4j
@Configuration
public class PayClientConfig {
    
    @Bean
    @Profile("default")
    public LocalPayClient localPayClient() {
        log.info("로컬 payClient 등록");
        return new LocalPayClient();
    }
    
    @Bean
    @Profile("prod")
    public ProdPayClient prodPayClient() {
        log.info("프로덕션 payClient 등록");
        return new ProdPayClient();
    }
}
```

- 이렇게 하여 프로필에 따라 다른 의존관계를 주입해줄 수 있다.
