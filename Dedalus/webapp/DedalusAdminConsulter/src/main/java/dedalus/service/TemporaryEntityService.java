package dedalus.service;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import dedalus.domain.IdentifiableEntity;

@Service
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TemporaryEntityService<U extends Serializable> {
	private U entity;
	
	public TemporaryEntityService<U> init(U entity) {
		this.entity = entity;
		
		return this;
	}
	
	public void clear() {
		this.entity = null;
	}
	
	public U getEntity() {
		return entity;
	}
	
	public TemporaryEntityService<U> setEntity(U entity) {
		this.entity = entity;
		
		return this;
	}
}