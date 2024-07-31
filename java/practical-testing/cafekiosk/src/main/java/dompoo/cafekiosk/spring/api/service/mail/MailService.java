package dompoo.cafekiosk.spring.api.service.mail;

import dompoo.cafekiosk.spring.client.mail.MailSendClient;
import dompoo.cafekiosk.spring.domain.history.mail.MailSendHistory;
import dompoo.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {
	
	private final MailSendClient mailSendClient;
	private final MailSendHistoryRepository mailSendHistoryRepository;
	
	public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
		
		boolean result = mailSendClient.sendMail(fromEmail, toEmail, subject, content);
		
		if (result) {
			mailSendHistoryRepository.save(MailSendHistory.builder()
					.fromEmail(fromEmail)
					.toEmail(toEmail)
					.subject(subject)
					.content(content)
					.build());
		}
		
		return result;
	}
}
