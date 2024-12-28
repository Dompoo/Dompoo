import java.util.Optional;

public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void registerUser(Long id, String name) {
		Optional<User> byId = userRepository.findById(id);
		
		if (byId.isPresent()) {
			throw new RuntimeException("이미 있는 id입니다.");
		}
		
		userRepository.save(id, name);
	}
	
}
