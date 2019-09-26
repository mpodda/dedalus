package dedalus.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Result;
import dedalus.domain.Token;
import dedalus.repository.AbstractJpaDao;
import dedalus.repository.ResultRepository;

@Service
public class ResultService extends IdentifiableEntityService<Result> {
	private ResultRepository resultRepository;
	
	@Autowired
	public ResultService(ResultRepository resultRepository) {
		this.resultRepository = resultRepository;
	}

	@Override
	public AbstractJpaDao<Result> getRepository() {
		return this.resultRepository;
	}
	
	public Result findByToken(Token token) {
		return this.resultRepository.findByToken(token.getId());
	}

}
