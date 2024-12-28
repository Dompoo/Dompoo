package dompoo.controller_advice_demo.code;

import static dompoo.controller_advice_demo.exception.ErrorEnum.*;

import dompoo.controller_advice_demo.exception.ErrorEnum;
import dompoo.controller_advice_demo.exception.MyException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	private static int count = 0;
	
	public String doLogic() {
		count++;
		
		if (count % 5 == 0) {
			throw new MyException(COUNT_IS_FIVE_MUL);
		} else {
			return "현재 count : " + count;
		}
		
	}
	
	
}
