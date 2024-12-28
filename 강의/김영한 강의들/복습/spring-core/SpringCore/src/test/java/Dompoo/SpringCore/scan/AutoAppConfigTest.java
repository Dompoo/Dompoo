package Dompoo.SpringCore.scan;

import Dompoo.SpringCore.AutoAppConfig;
import Dompoo.SpringCore.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {
    
    @Test
    void basicScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        //빈 이름은 기본적으로 클래스명을 캐멀케이스로 : MemberServiceImpl -> memberServiceImpl
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
