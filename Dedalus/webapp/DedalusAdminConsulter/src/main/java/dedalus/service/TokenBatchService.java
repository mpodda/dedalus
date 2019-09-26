package dedalus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.ApplicationStringConstants;
import dedalus.domain.TuitionCentre;
import dedalus.domain.structures.TokenBatch;
import dedalus.service.api.TokenService;

@Service
public class TokenBatchService {
	private TokenService tokenService;
	
	@Autowired
	public TokenBatchService(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
	public TokenBatch createTokenBatch(TuitionCentre tuitionCentre) {
		String batchCode = String.format("%s_%s_%s", 
				ApplicationStringConstants.BATCH_CODE_PREFIX.getValue(),
				tuitionCentre.getId(),
				tokenService.countBatches(tuitionCentre) + 1);
		
		return new TokenBatch.Builder(tuitionCentre, batchCode).build();
	}
}
