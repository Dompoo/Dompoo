package hello;

import hello.boot.MySpringApplication;
import hello.boot.MySpringBootApplication;

@MySpringBootApplication
public class MySpringBootMain {
	
	public static void main(String[] args) {
		System.out.println("MySpringBootMain.main");
		
		// 이 클래스를 빈 등록하므로, 이 클래스와 그 하위 클래스들도 컴포넌트 스캔의 대상이 된다.
		MySpringApplication.run(MySpringBootMain.class, args);
	}
}
