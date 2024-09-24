package Dompoo.advanced.app.v3;

import Dompoo.advanced.app.trace.TraceId;
import Dompoo.advanced.app.trace.TraceStatus;
import Dompoo.advanced.app.trace.hellotrace.HelloTraceV2;
import Dompoo.advanced.app.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {

    private final LogTrace trace;

    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("orderRepository.save");
            sleep(1000);
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
