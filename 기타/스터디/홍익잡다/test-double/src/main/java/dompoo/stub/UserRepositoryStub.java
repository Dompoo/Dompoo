package dompoo.stub;

import dompoo.User;
import dompoo.UserRepository;

import java.util.Optional;

public class UserRepositoryStub implements UserRepository {
	
	@Override
	public Optional<User> findById(Long id) {
		return Optional.empty();
	}
	
	@Override
	public User save(Long id, String name) {
		return new User(1L, "창근");
	}
}
