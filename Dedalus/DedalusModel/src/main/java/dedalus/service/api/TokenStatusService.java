package dedalus.service.api;

import org.springframework.stereotype.Service;

import dedalus.domain.Token;
import dedalus.enums.TokenStatus;

@Service
public class TokenStatusService {
	public Integer initStatus() {
		return TokenStatus.Available.getStatus();
	}
	
	//TODO: Throws exception
	public Token notStarted(Token token) {
		if (token.getPupil() != null && token.getStatus().equals(TokenStatus.Available.getStatus())) {
			token.setStatus(TokenStatus.NotStarted.getStatus());
		}
		
		return token;
	}
	
	public Token onGoing(Token token) {
		if (token.getPupil() != null && token.getStatus().equals(TokenStatus.NotStarted.getStatus())) {
			token.setStatus(TokenStatus.OnGoing.getStatus());
		}
		
		return token;
	}
	
	public Token completed(Token token) {
		if (token.getPupil() != null && token.getStatus().equals(TokenStatus.OnGoing.getStatus())) {
			token.setStatus(TokenStatus.Completed.getStatus());
		}
		
		return token;
	}
	
	public Token fromOnGoingToNotStarted(Token token) {
		if (token.getPupil() != null && token.getStatus().equals(TokenStatus.OnGoing.getStatus())) {
			token.setStatus(TokenStatus.NotStarted.getStatus());
		}
		
		return token;
	}
}
