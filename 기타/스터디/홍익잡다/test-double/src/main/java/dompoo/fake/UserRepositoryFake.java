package dompoo.fake;

import dompoo.User;
import dompoo.UserRepository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryFake implements UserRepository {
	
	private List<User> repository = new ArrayList<>();
	
	@Override
	public Optional<User> findById(Long id) {
		List<User> list = repository.stream()
				.filter(user -> user.id.equals(id))
				.toList();
		
		if (list.isEmpty()) return Optional.empty();
		else return Optional.of(list.getFirst());
	}
	
	@Override
	public User save(Long id, String name) {
		repository.add(new User(id, name));
		return repository.stream()
				.filter(user -> user.id.equals(id))
				.toList()
				.getFirst();
	}
}
