package dedalus.service.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import dedalus.domain.Parameter;
import dedalus.repository.AbstractJpaDao;
import dedalus.repository.ParameterRepository;

@Service
public abstract class ParameterService <P extends Parameter> extends IdentifiableEntityService <P>{
	public abstract ParameterRepository <P> getParameterRepository();

	public P create() {
		return this.getParameterRepository().createEntity();
	}	
	
	public P save (P parameter) {
		return this.getParameterRepository().merge(parameter);
	}
	
	public void delete(P parameter) {
		this.getParameterRepository().delete(this.getParameterRepository().findOne(parameter.getId()));
	} 
	
	public List<P> findAll() {
		List<P> list = new ArrayList<>();
		
		this.getParameterRepository().findAll().forEach (
			p -> list.add((P) p)
		);
		
		return list;
	}
	
	public P get (P parameter) {
		return this.getParameterRepository().findOne(parameter.getId());
	}
	
	@Override
	public AbstractJpaDao<P> getRepository() {
		return this.getParameterRepository();
	}
	
}
