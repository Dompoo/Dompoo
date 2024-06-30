package Dompoo.advanced.app.v4;

import Dompoo.advanced.app.trace.logtrace.LogTrace;
import Dompoo.advanced.app.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {

    private final LogTrace trace;

    public void save(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                sleep(1000);
                if (itemId.equals("ex")) {
                    throw new IllegalStateException("예외 발생!");
                }
                return null;
            }
        };

        template.execute("orderRepository.save");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
