package dedalus.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.User;
import dedalus.repository.AbstractJpaDao;
import dedalus.repository.UserRepository;

@Service
public class UserService extends IdentifiableEntityService<User>{
	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public int countUsers() {
		return this.userRepository.countUsers();
	}

	public User findByUserName(String userName) {
		return this.userRepository.findByUserName(userName);
	}
	
	@Override
	public AbstractJpaDao<User> getRepository() {
		return this.userRepository;
	}
}
