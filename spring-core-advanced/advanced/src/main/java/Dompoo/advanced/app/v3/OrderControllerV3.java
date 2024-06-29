package Dompoo.advanced.app.v3;

import Dompoo.advanced.app.trace.TraceStatus;
import Dompoo.advanced.app.trace.hellotrace.HelloTraceV2;
import Dompoo.advanced.app.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("orderController.request");
            //계속해서 파라미터로 TraceId를 넘겨야 한다. -> 수정해야 하는 클래스가 오억개라면?
            orderService.orderItem(itemId);
            trace.end(status);
            return "OK";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 던져주어야 애플리케이션 흐름을 바꾸지 않는 것!
        }
    }
}
