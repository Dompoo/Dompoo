package dompoo.advanced.trace.template;

import dompoo.advanced.trace.TraceStatus;
import dompoo.advanced.trace.logTrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public T execute(String message) {
        TraceStatus status = null;

        try {
            status = trace.begin("OrderService.orderItem()");

            //로직호출 -> 추상화
            T result = call();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();
}
