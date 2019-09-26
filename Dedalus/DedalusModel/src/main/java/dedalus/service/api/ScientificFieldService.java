package dedalus.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.ScientificField;
import dedalus.repository.ParameterRepository;
import dedalus.repository.ScientificFieldRepository;

@Service
public class ScientificFieldService extends ParameterService<ScientificField> {
	private ScientificFieldRepository scientificFieldRepository;

	@Autowired
	public ScientificFieldService(ScientificFieldRepository scientificFieldRepository) {
		this.scientificFieldRepository = scientificFieldRepository;
	}
	
	@Override
	public ParameterRepository<ScientificField> getParameterRepository() {
		return this.scientificFieldRepository;
	}
}
