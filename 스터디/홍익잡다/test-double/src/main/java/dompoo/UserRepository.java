package dompoo;

import java.util.Optional;

public interface UserRepository {
	
	Optional<User> findById(Long id);
	
	User save(Long id, String name);
}
