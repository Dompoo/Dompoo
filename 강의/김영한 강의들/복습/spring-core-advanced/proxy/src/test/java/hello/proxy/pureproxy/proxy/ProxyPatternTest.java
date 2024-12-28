package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);

        client.execute();
        client.execute();
        client.execute();
        //세번 호출하면 3초 걸리게 된다.
        //하지만 호출마다 같은 데이터가 조회된다면, 어딘가에 캐시할 수는 없을까?
    }

    @Test
    void cacheProxyTest() {
        RealSubject target = new RealSubject();
        CacheProxy proxy = new CacheProxy(target);
        ProxyPatternClient client = new ProxyPatternClient(proxy);

        client.execute();
        client.execute();
        client.execute();
        //기존 코드를 하나도 수정하지 않고 프록시를 사이에 넣는다.
        //RealSubject는 단 1번 호출되고, 나머지는 프록시가 값을 반환해준다.
        //한번 호출되므로 시간은 1초 걸리게 된다.
    }
}
