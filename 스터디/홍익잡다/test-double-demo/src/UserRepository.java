import java.util.Optional;

public interface UserRepository {
	
	Optional<User> findById(Long id);
	
	void save(Long id, String name);
}
