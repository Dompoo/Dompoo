package dompoo.mock;

import dompoo.User;
import dompoo.UserRepository;
import dompoo.dummy.UserRepositoryDummy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryMock extends UserRepositoryDummy {
	
	public int count = 0;
	
	@Override
	public Optional<User> findById(Long id) {
		count++;
		return super.findById(id);
	}
	
	@Override
	public User save(Long id, String name) {
		count++;
		return super.save(id, name);
	}
}
