package hello.proxy.pureproxy.concrete_proxy;

import hello.proxy.pureproxy.concrete_proxy.code.ConcreteClient;
import hello.proxy.pureproxy.concrete_proxy.code.ConcreteLogic;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic logic = new ConcreteLogic();

        ConcreteClient client = new ConcreteClient(logic);

        client.execute();
    }
}
