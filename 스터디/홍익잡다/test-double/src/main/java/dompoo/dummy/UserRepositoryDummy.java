package dompoo.dummy;

import dompoo.User;
import dompoo.UserRepository;

import java.util.Optional;

public class UserRepositoryDummy implements UserRepository {
	
	@Override
	public Optional<User> findById(Long id) {
		return Optional.empty();
	}
	
	@Override
	public User save(Long id, String name) {
		return null;
	}
}
