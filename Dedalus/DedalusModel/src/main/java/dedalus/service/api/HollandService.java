package dedalus.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Holland;
import dedalus.repository.AbstractJpaDao;
import dedalus.repository.HollandRepository;
import dedalus.repository.ParameterRepository;

@Service
public class HollandService extends ParameterService<Holland> {
	private HollandRepository hollandRepository; 
	
	@Autowired
	public HollandService(HollandRepository hollandRepository) {
		this.hollandRepository = hollandRepository;
	}
	
	@Override
	public AbstractJpaDao<Holland> getRepository() {
		return this.hollandRepository;
	}

	@Override
	public ParameterRepository<Holland> getParameterRepository() {
		return this.hollandRepository;
	}
	
	public Holland getByValue(int value) {
		return this.hollandRepository.getByValue(value);
	}

}