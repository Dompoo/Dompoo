package dompoo;

import java.util.Optional;

public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User registerUser(Long id, String name) {
		Optional<User> byId = userRepository.findById(id);
		
		if (byId.isPresent()) {
			throw new RuntimeException("이미 있는 id입니다.");
		}
		
		return userRepository.save(id, name);
	}
	
}
