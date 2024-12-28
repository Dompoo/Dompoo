package dompoo.cafekiosk.spring.api.service.mail;

import dompoo.cafekiosk.spring.client.mail.MailSendClient;
import dompoo.cafekiosk.spring.domain.history.mail.MailSendHistory;
import dompoo.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class) //테스트가 시작될 때 Mockito로 클래스 생성 가능
class MailServiceTest {
	
	@Mock
	private MailSendClient mailSendClient;
	
	@Mock
	private MailSendHistoryRepository mailSendHistoryRepository;
	
	@InjectMocks //DI랑 비슷, 목객체를 주입한다.
	private MailService mailService;

	@Test
	@DisplayName("메일 전송 테스트")
	void sendMail() {
	    //given
//		when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
//				.thenReturn(true);
//		doReturn(true)
//				.when(mailSendClient)
//				.sendMail(anyString(), anyString(), anyString(), anyString());
		given(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
				.willReturn(true);
		/* Mock
		Mock 객체에 아무것도 지정하지 않으면, 예외를 발생시키는 것이 아니라 기본값을 반환한다. (null등)
		또한 when절을 통해 스터빙을 할 수 있다.
		 */
		/* Spy
		Spy 객체에 아무것도 지정하지 않으면, 기존 객체의 행동을 한다.
		즉 필요한 메서드만 스터빙하고, 나머진 유지할 수 있다.
		do~ 절을 통해 스터빙을 할 수 있다.
		 */
		
		//when
		boolean result = mailService.sendMail("", "", "", "");
		
		//then
		Assertions.assertThat(result).isTrue();
		//save가 1번 불렸는지 검증
//		verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
		then(mailSendHistoryRepository).should(times(1)).save(any(MailSendHistory.class));
	}
}