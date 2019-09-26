package dedalus.domain.structures;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import dedalus.enums.TokenStatus;

public class AvailableToken implements Serializable {
	private static final long serialVersionUID = -8416171494072086465L;

	TokenStatus status;
	Integer availableTokens;
	Integer totalTokens;
	
	public AvailableToken() {
		
	}

	public AvailableToken(TokenStatus status, Integer availableTokens, Integer totalTokens) {
		this.status = status;
		this.availableTokens = availableTokens;
		this.totalTokens = totalTokens;
	}
	
	public TokenStatus getStatus() {
		return status;
	}

	public void setStatus(TokenStatus status) {
		this.status = status;
	}
	
	
	public Integer getAvailableTokens() {
		return availableTokens;
	}

	public void setAvailableTokens(Integer availableTokens) {
		this.availableTokens = availableTokens;
	}

	public Integer getTotalTokens() {
		return totalTokens;
	}

	public void setTotalTokens(Integer totalTokens) {
		this.totalTokens = totalTokens;
	}

	@JsonProperty
	public Integer getPercentage() {
		if (this.totalTokens > 0) {
			return new Double ((new Double (this.availableTokens) / this.totalTokens) * 100).intValue();
		}
		
		return 0;
	}
}
