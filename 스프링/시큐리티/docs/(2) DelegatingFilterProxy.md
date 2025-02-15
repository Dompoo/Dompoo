# DelegatingFilterProxy

- `DelegatingFilterProxy`는 시큐리티 의존성 추가시 자동 등록된다.

```java
@AutoConfiguration(
    after = {SecurityAutoConfiguration.class}
)
@ConditionalOnWebApplication(
    type = Type.SERVLET
)
@EnableConfigurationProperties({SecurityProperties.class})
@ConditionalOnClass({AbstractSecurityWebApplicationInitializer.class, SessionCreationPolicy.class})
public class SecurityFilterAutoConfiguration {
    private static final String DEFAULT_FILTER_NAME = "springSecurityFilterChain";

    public SecurityFilterAutoConfiguration() {
    }

    @Bean
    @ConditionalOnBean(
        name = {"springSecurityFilterChain"}
    )
    public DelegatingFilterProxyRegistrationBean securityFilterChainRegistration(SecurityProperties securityProperties) {
        DelegatingFilterProxyRegistrationBean registration = new DelegatingFilterProxyRegistrationBean("springSecurityFilterChain", new ServletRegistrationBean[0]);
        registration.setOrder(securityProperties.getFilter().getOrder());
        registration.setDispatcherTypes(this.getDispatcherTypes(securityProperties));
        return registration;
    }

    private EnumSet<DispatcherType> getDispatcherTypes(SecurityProperties securityProperties) {
        return securityProperties.getFilter().getDispatcherTypes() == null ? null : (EnumSet)securityProperties.getFilter().getDispatcherTypes().stream().map((type) -> DispatcherType.valueOf(type.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(DispatcherType.class)));
    }
}
```

- 위 코드는 서블릿 웹 애플리케이션에서 자동 등록되는 `DelegatingFilterProxy`의 모습이다.
