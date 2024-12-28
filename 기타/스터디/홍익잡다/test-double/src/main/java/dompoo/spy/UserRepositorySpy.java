package dompoo.spy;

import dompoo.User;
import dompoo.UserRepository;
import dompoo.fake.UserRepositoryFake;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositorySpy extends UserRepositoryFake {
	
	public int count;
	
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
