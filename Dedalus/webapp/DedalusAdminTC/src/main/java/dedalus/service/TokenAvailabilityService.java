package dedalus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.structures.AvailableToken;
import dedalus.enums.TokenStatus;
import dedalus.repository.TokenRepository;

@Service
public class TokenAvailabilityService {
	private UserSessionService userSessionService;
	private TokenRepository tokenRepository; 
	
	@Autowired
	public TokenAvailabilityService(UserSessionService userSessionService,
			TokenRepository tokenRepository) {
		this.userSessionService = userSessionService;
		this.tokenRepository = tokenRepository;
	}
	
	public AvailableToken getAvailableTokens() {
		int totalAvailableTokens = tokenRepository.countTotalAvailable (userSessionService.getUser().getTuitionCentre().getId(), TokenStatus.Completed.getStatus());
		
		if (totalAvailableTokens > 0) {
			int availableTokens = tokenRepository.countTokensByStatus(userSessionService.getUser().getTuitionCentre().getId(), TokenStatus.Available.getStatus());
			return new AvailableToken(TokenStatus.Available, availableTokens, totalAvailableTokens);
		}
		
		return new AvailableToken(TokenStatus.Available, 0, 0);
	}
	
	public AvailableToken getNotStartedTokens() {
		int totalAssignedTokens = tokenRepository.countTotalAssigned(userSessionService.getUser().getTuitionCentre().getId(), 
				TokenStatus.NotStarted.getStatus(), 
				TokenStatus.OnGoing.getStatus());
		
		if (totalAssignedTokens > 0) {
			int notStartedTokens = tokenRepository.countTokensByStatus(userSessionService.getUser().getTuitionCentre().getId(), TokenStatus.NotStarted.getStatus());
			return new AvailableToken(TokenStatus.NotStarted, notStartedTokens, totalAssignedTokens);
		}
		
		return new AvailableToken(TokenStatus.NotStarted, 0, 0);
	}
	
	public AvailableToken getOnGoingTokens() {
		int totalAssignedTokens = tokenRepository.countTotalAssigned(userSessionService.getUser().getTuitionCentre().getId(), 
				TokenStatus.NotStarted.getStatus(), 
				TokenStatus.OnGoing.getStatus());
		
		if (totalAssignedTokens > 0) {
			int notStartedTokens = tokenRepository.countTokensByStatus(userSessionService.getUser().getTuitionCentre().getId(), TokenStatus.OnGoing.getStatus());
			return new AvailableToken(TokenStatus.OnGoing, notStartedTokens, totalAssignedTokens);
		}
		
		return new AvailableToken(TokenStatus.OnGoing, 0, 0);
	}
	
	public AvailableToken getCompletedTokens() {
		int totalAssignedAndCompletedTokens =  tokenRepository.countTotalAssignedAndCompleted(userSessionService.getUser().getTuitionCentre().getId(), 
				TokenStatus.NotStarted.getStatus(), 
				TokenStatus.OnGoing.getStatus(), 
				TokenStatus.Completed.getStatus());
		
		if (totalAssignedAndCompletedTokens > 0) {
			int comletedTokens = tokenRepository.countCompleted(userSessionService.getUser().getTuitionCentre().getId(), TokenStatus.Completed.getStatus());
			return new AvailableToken(TokenStatus.Completed, comletedTokens, totalAssignedAndCompletedTokens);
		}
		
		return new AvailableToken(TokenStatus.Completed, 0, 0);
	}
}
