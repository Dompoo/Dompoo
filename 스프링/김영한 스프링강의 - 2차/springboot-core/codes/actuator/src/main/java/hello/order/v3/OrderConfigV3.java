package hello.order.v3;

import hello.order.OrderService;
import hello.order.v0.OrderServiceV0;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigV3 {
    
    @Bean
    public OrderService orderService() {
        return new OrderServiceV3();
    }
    
    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry) {
        return new TimedAspect(meterRegistry);
    }
}
