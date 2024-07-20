package Dompoo.advanced.app.v5;

import Dompoo.advanced.app.trace.callback.TraceTemplate;
import Dompoo.advanced.app.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate traceTemplate;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.traceTemplate = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {
        traceTemplate.execute("orderService.orderItem", () -> {
            orderRepository.save(itemId);
            return null;
        });
    }
}
