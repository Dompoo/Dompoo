package Dompoo.advanced.app.trace.logtrace;

import Dompoo.advanced.app.trace.TraceId;
import Dompoo.advanced.app.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

//싱글톤 빈이기 때문에 여러 요청이 한꺼번에 들어왔을 때, 동시성 문제가 발생한다.
//즉, 여러 쓰레드가 같은 빈 객체를 사용하여 필드에 저장된 TraceId를 공유하기 때문에, 문제가 발생한다.
@Slf4j
public class ThreadLocalLogTrace implements LogTrace {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>(); //traceId를 들고있어서 동기화를 해준다. But 동시성 이슈 고려 필요

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceIdHolder.get().createNextId());
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }

        releaseTraceId(); //단계를 줄이거나 마지막엔 null을 넣는다.
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();

        if (traceId.isFirstLevel()) {
            traceIdHolder.remove(); //remove되지 않으면 다음에 해당 쓰레드를 사용하는 클라이언트에게 정보가 노출될 수 있다.
        } else {
            traceIdHolder.set(traceId.createPrevId());
        }
    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
