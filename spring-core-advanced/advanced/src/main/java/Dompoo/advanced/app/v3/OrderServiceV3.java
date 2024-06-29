package Dompoo.advanced.app.v3;

import Dompoo.advanced.app.trace.TraceId;
import Dompoo.advanced.app.trace.TraceStatus;
import Dompoo.advanced.app.trace.hellotrace.HelloTraceV2;
import Dompoo.advanced.app.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("orderService.orderItem");
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
