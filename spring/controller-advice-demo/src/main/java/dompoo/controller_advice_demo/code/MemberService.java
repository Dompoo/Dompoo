package dompoo.controller_advice_demo.code;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	private static int count = 0;
	
	public String doLogic() {
		count++;
		
		if (count % 5 == 0) {
			throw new IllegalStateException("count가 5의 배수입니다.");
		} else {
			return "현재 count : " + count;
		}
	}
}
