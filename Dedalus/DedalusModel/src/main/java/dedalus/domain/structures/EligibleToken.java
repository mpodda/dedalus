package dedalus.domain.structures;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dedalus.domain.IdentifiableEntity;
import dedalus.domain.Token;

public class EligibleToken extends EligibleEntry implements Serializable {
	private static final long serialVersionUID = -1273303815434793222L;
	
	private Token token;
	
	public EligibleToken() {
		super(false);
	}
	
	public EligibleToken(Token token, boolean selected) {
		super(selected);
		this.token = token;
	}
	
	public EligibleToken(Token token) {
		super(false);
		this.token = token;
	}
	
	@JsonIgnore
	@Override
	public IdentifiableEntity getEntry() {
		return this.token;
	}

	@Override
	public void setEntry(IdentifiableEntity entry) {
		this.token = (Token) entry;
	}
	
	public Token getToken() {
		return token;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}
}
