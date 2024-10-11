package dompoo;

import dompoo.dummy.UserRepositoryDummy;
import dompoo.fake.UserRepositoryFake;
import dompoo.mock.UserRepositoryMock;
import dompoo.spy.UserRepositorySpy;
import dompoo.stub.UserRepositoryStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
	
	@Test
	void test1() {
		UserRepositorySpy repo = new UserRepositorySpy();
		UserService service = new UserService(repo);
		
		User savedUser = service.registerUser(1L, "창근");
		assertThrows(RuntimeException.class,
				() -> service.registerUser(1L, "창근"));
		assertEquals(3, repo.count);
	}
	
}