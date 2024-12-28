package dompoo.jdbc.service;

import dompoo.jdbc.domain.Member;
import dompoo.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {
	
	private final MemberRepositoryV1 memberRepository;
	
	public void accountTransfer(String fromId, String toId, int money) throws SQLException, InterruptedException {
		Member fromMember = memberRepository.findById(fromId);
		Member toMember = memberRepository.findById(toId);
		
		memberRepository.update(fromId, fromMember.getMoney() - money);
		validation(toMember);
		memberRepository.update(toId, toMember.getMoney() + money);
	}
	
	private static void validation(Member toMember) {
		if (toMember.getMemberId() == "ex") {
			throw new IllegalStateException("이체중 문제 발생!");
		}
	}
	
	
}
