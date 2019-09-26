package dedalus.service.api;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import dedalus.domain.Pupil;
import dedalus.domain.Questionnaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Token;
import dedalus.domain.TuitionCentre;
import dedalus.domain.search.SearchTokenCriteria;
import dedalus.domain.structures.EligibleToken;
import dedalus.domain.structures.TokenBatch;
import dedalus.repository.AbstractJpaDao;
import dedalus.repository.TokenRepository;

@Service
public class TokenService extends IdentifiableEntityService<Token> {
	private TokenRepository tokenRepository;
	private TokenStatusService tokenStatusService; 
	
	@Autowired
	public TokenService(TokenRepository tokenRepository, TokenStatusService tokenStatusService) {
		this.tokenRepository = tokenRepository;
		this.tokenStatusService = tokenStatusService;
	}
	
	@Override
	public AbstractJpaDao<Token> getRepository() {
		return this.tokenRepository;
	}

	public void addTokens(TokenBatch tokenBatch) {
		if (tokenBatch.getAmount() != null && tokenBatch.getAmount() > 0) {
			for (int tokenIndex = 0; tokenIndex < tokenBatch.getAmount().intValue(); tokenIndex++) {
				Token token = new Token();
				token.setBatch(tokenBatch.getBatchCode());
				token.setCreationDate(tokenBatch.getCreationDate());
				token.setNumber(tokenIndex+1);
				token.setPayed(tokenBatch.getPayed());
				token.setQuestionnaire(tokenBatch.getQuestionnaire());
				token.setTuitionCentre(tokenBatch.getTuitionCentre());
				token.setValue(UUID.randomUUID().toString());
				token.setStatus(tokenStatusService.initStatus());
				
				this.tokenRepository.save(token);
			}
		}
	}
	
	public List<EligibleToken> searchTokens(SearchTokenCriteria tokenCriteria) {
		
		List<EligibleToken> eligibleTokens = new ArrayList<>();
		
		this.tokenRepository.search(tokenCriteria).forEach (
			t -> eligibleTokens.add(new EligibleToken(t))
		);
		
		return eligibleTokens;
	}
	
	public SearchTokenCriteria createSearchTokenCriteria() {
		return new SearchTokenCriteria();
	}
	
	public SearchTokenCriteria createSearchTokenCriteria(TuitionCentre tuitionCentre) {
		return new SearchTokenCriteria(tuitionCentre);
	}

	public SearchTokenCriteria createSearchTokenCriteriaForLast30Days(TuitionCentre tuitionCentre) {
		SearchTokenCriteria criteria = new SearchTokenCriteria(tuitionCentre);
		
		Instant today = new Date().toInstant();
		Instant _30DaysBefore = today.plus(-30, ChronoUnit.DAYS);

		criteria.setCreationDateFrom(Date.from(_30DaysBefore));
		criteria.setCreationDateTo(Date.from(today));
		
		return criteria;
	}
	
	
	public SearchTokenCriteria initSearchTokenCriteria(SearchTokenCriteria searchTokenCriteria) {
		searchTokenCriteria.init();
		
		return searchTokenCriteria;
	}
	
	public int countBatches(TuitionCentre tuitionCentre) {
		int size = tokenRepository.batches(tuitionCentre.getId()).size();
//		System.out.println(String.format("Size = %s", size));
		return size;
	}
	
	public List<EligibleToken> markTokensAs(List<EligibleToken> tokens, boolean payed) {
		
		tokens.forEach (
			t -> {
				if (t.isSelected()) {
					t.setToken(this.get(t.getToken()));
					t.getToken().setPayed(payed);
					t.setToken(this.save(t.getToken()));
				}
			}
		);
		
		return tokens; 
	}

	public List<EligibleToken> setEligibleTokensAs(List<EligibleToken> tokens, boolean selected) {
		tokens.forEach (
			t -> {
				t.setSelected(selected);
			}
		);
		
		return tokens;
	}
	
	public List<EligibleToken> setEligiblePayedTokensAs(List<EligibleToken> tokens, boolean selected) {
		tokens.forEach (
			t -> {
				if (t.getToken().getPayed()) {
					t.setSelected(selected);
				} else {
					t.setSelected(!selected);
				}
			}
		);
		
		return tokens;
	}
	
	public List<EligibleToken> setEligibleNotPayedTokensAs(List<EligibleToken> tokens, boolean selected) {
		tokens.forEach (
			t -> {
				if (!t.getToken().getPayed()) {
					t.setSelected(selected);
				} else {
					t.setSelected(!selected);
				}
			}
		);
		
		return tokens;
	}

	public List<Token> byTuitionCentre(TuitionCentre tuitionCentre) {
		return tokenRepository.byTuitionCentre(tuitionCentre.getId());
	}

	public List<Token> byTuitionCentre(TuitionCentre tuitionCentre, boolean available) {
		return tokenRepository.byTuitionCentre(tuitionCentre.getId()).stream().filter(
				token ->  token.isAvailable() == available
		).collect(Collectors.toList());
	}

	public List<Token> byTuitionCentreAndQuestionnaire(TuitionCentre tuitionCentre, Questionnaire questionnaire, boolean available) {
		return tokenRepository.byTuitionCentre(tuitionCentre.getId()).stream().filter(
				token ->  
				token.getQuestionnaire() != null && token.isAvailable() == available && token.getQuestionnaire().equals(questionnaire)
				
		).collect(Collectors.toList());
	}
	
	public List<Token> byPupil(Pupil pupil) {
		return tokenRepository.byPupil(pupil.getId());
	}

	public Token byCode(String code) {
		return tokenRepository.byCode(code);
	}
	
	
	/*
	public static void main(String[] args) {
		Instant today = new Date().toInstant();
		
		Instant _30DaysBefore = today.plus(-30, ChronoUnit.DAYS);
		
		System.out.println(_30DaysBefore);
		Date d = Date.from(_30DaysBefore);
		
		
	}
	*/
}