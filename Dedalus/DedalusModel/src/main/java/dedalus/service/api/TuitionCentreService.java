package dedalus.service.api;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Token;
import dedalus.domain.TuitionCentre;
import dedalus.domain.search.SearchTokenCriteria;
import dedalus.domain.structures.SendTokenRequest;
import dedalus.enums.TokenStatus;
import dedalus.repository.ParameterRepository;
import dedalus.repository.TokenRepository;
import dedalus.repository.TuitionCentreRepository;

@Service
public class TuitionCentreService extends ParameterService<TuitionCentre> {
	private TuitionCentreRepository tuitionCentreRepository; 
	private TokenRepository tokenRepository;
	private TokenService tokenService;

	public ParameterRepository<TuitionCentre> getParameterRepository() {
		return this.tuitionCentreRepository;
	}
	
	@Autowired
	public TuitionCentreService(TuitionCentreRepository tuitionCentreRepository,
								TokenRepository tokenRepository,
								TokenService tokenService) {
		this.tuitionCentreRepository = tuitionCentreRepository;
		this.tokenRepository = tokenRepository;
		this.tokenService = tokenService;
	}
	
	public TuitionCentre byUserName(String userName) {
		return this.tuitionCentreRepository.byUserName(userName);
	}

	public List<Token> getTokens(TuitionCentre tuitionCentre){
		return this.tokenRepository.byTuitionCentre(tuitionCentre.getId());
	}

	public int countUsedTokens(TuitionCentre tuitionCentre) {
		AtomicInteger numberOfUsedTokens = new AtomicInteger(0);

		List<Token> allTokens = this.getTokens(tuitionCentre);

		//Only tokens from batches with available tokens
		getBatches(tuitionCentre).forEach(
			batch -> {
				List<Token> allTokensOfBatch = allTokens.stream().filter( t-> t.getBatch().equals(batch)).collect(Collectors.toList());
				List<Token> usedTokensOfBatch = allTokens.stream().filter( t-> t.getBatch().equals(batch) && !t.isAvailable()).collect(Collectors.toList());

				//Still some available tokens
				if (usedTokensOfBatch.size() < allTokensOfBatch.size()){
					numberOfUsedTokens.addAndGet(usedTokensOfBatch.size());
				}
			}
		);

		return numberOfUsedTokens.get();
	}

	public int countAvailableTokens(TuitionCentre tuitionCentre) {
		/*
		return this.getTokens(tuitionCentre)
				.stream()
				.filter( t-> t.isAvailable())
				.collect(Collectors.toList())
				.size();
		*/
		
		
		
		return this.getTokens(tuitionCentre)
				.stream()
				.filter( t-> t.getStatus() == TokenStatus.Available.getStatus())
				.collect(Collectors.toList())
				.size();
		
	}

	public int countNotAnsweredTokens(TuitionCentre tuitionCentre){
		//TODO: Implement later. Pupil Session is required.

		return 0;
	}

	public int countTokensByStatus(TuitionCentre tuitionCentre, Integer status) {
		return this.getTokens(tuitionCentre)
				.stream()
				.filter( t-> t.getStatus().equals(status))
				.collect(Collectors.toList())
				.size();		
	} 
	
	public SearchTokenCriteria createSearchTokenCriteria(TuitionCentre tuitionCentre) {
		SearchTokenCriteria searchTokenCriteria = new SearchTokenCriteria();
		searchTokenCriteria.setTuitionCentre(tuitionCentre);


		return  searchTokenCriteria;
	}

	public List<Token> tokensHistory(SearchTokenCriteria tokenCriteria){
		if (!tokenCriteria.hasAvailable()) {
			return tokenRepository.search(tokenCriteria);
		}

		return tokenRepository.search(tokenCriteria).stream().filter (
				t -> t.isAvailable() == tokenCriteria.getAvaiable()
		).collect(Collectors.toList());
	}

	public List<String> getBatches(TuitionCentre tuitionCentre) {
		return tokenRepository.batches(tuitionCentre.getId());
	}

	public Optional <Token> getNextAvailableToken(SendTokenRequest sendTokenRequest) {
		List<Token> availableTokens = tokenService.byTuitionCentreAndQuestionnaire(sendTokenRequest.getPupil().getTuitionCentre(), sendTokenRequest.getQuestionnaire(), true);
		
		Optional <Token> token = Optional.empty();
				
		if (!availableTokens.isEmpty()) {
			token = Optional.of(availableTokens.get(0));
		}
		
		return token;
	}
	
	public Boolean userExists(String userName) {
		return this.tuitionCentreRepository.countByUsername(userName) > 0;
	}
	
	@Override
	public TuitionCentre save(TuitionCentre tuitionCentre) {
		if (tuitionCentre.getPassword() == null || tuitionCentre.getPassword().equals("")) {
			TuitionCentre tuitionCentrePrieviousVersion = this.get(tuitionCentre);
			if (tuitionCentrePrieviousVersion != null) {
				tuitionCentre.setPassword(tuitionCentrePrieviousVersion.getPassword());
			}
		}
		return super.save(tuitionCentre);
	}
}