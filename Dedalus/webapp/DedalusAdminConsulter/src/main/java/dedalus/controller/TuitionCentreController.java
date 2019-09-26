package dedalus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Questionnaire;
import dedalus.domain.TuitionCentre;
import dedalus.domain.search.SearchTokenCriteria;
import dedalus.domain.structures.EligibleToken;
import dedalus.domain.structures.TokenBatch;
import dedalus.service.TemporaryEntityService;
import dedalus.service.TokenBatchService;
import dedalus.service.api.QuestionnaireService;
import dedalus.service.api.TokenService;
import dedalus.service.api.TuitionCentreService;

@Controller
public class TuitionCentreController {
	private TuitionCentreService tuitionCentreService;
	private TokenBatchService tokenBatchService;
	private TokenService tokenService;
	private TemporaryEntityService<TokenBatch> temporaryEntityService;
	private QuestionnaireService questionnaireService;
	
	@Autowired
	public TuitionCentreController(TuitionCentreService tuitionCentreService, 
			TokenBatchService tokenBatchService,
			TokenService tokenService,
			TemporaryEntityService<TokenBatch> temporaryEntityService,
			QuestionnaireService questionnaireService) {
		this.tuitionCentreService = tuitionCentreService;
		this.tokenBatchService = tokenBatchService;
		this.tokenService = tokenService;
		this.temporaryEntityService = temporaryEntityService;
		this.questionnaireService = questionnaireService;
	}
	
	@RequestMapping("/app/tuitionCentres/tuitionCentres")
	public String tuitionCentres() {
		return "/app/tuitionCentres/tuitionCentres";
	}
	
	@RequestMapping("/data/tcs")
	public @ResponseBody List<TuitionCentre> getTuitionCentres() {
		return this.tuitionCentreService.findAll();
	}
	
	@RequestMapping(value="/data/tc/create", method = RequestMethod.POST)
	public @ResponseBody TuitionCentre create() {
		return this.tuitionCentreService.create();
	}
	
	@RequestMapping(value="/data/tc/save", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<TuitionCentre> save(@RequestBody TuitionCentre tuitionCentre) {
		
		ResponseEntity<TuitionCentre> responseEntity;
		
		try {
			responseEntity = new ResponseEntity<TuitionCentre>(this.tuitionCentreService.save(tuitionCentre), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<TuitionCentre>(HttpStatus.BAD_REQUEST);
		}
		
		return responseEntity;
		//return this.tuitionCentreService.save(tuitionCentre);
	}
	
	@RequestMapping(value="/data/tc/get", method = RequestMethod.POST)
	public @ResponseBody TuitionCentre get(@RequestBody TuitionCentre tuitionCentre) {
		return this.tuitionCentreService.get(tuitionCentre);
	}
	
	@RequestMapping(value="data/tc/delete", method = RequestMethod.POST)
	public @ResponseBody TuitionCentre deleteCategory(@RequestBody TuitionCentre tuitionCentre) {
		this.tuitionCentreService.delete(tuitionCentre);
		
		return tuitionCentre;
	}

	
	@RequestMapping("/data/tc/createTokenBatch")
	public @ResponseBody TokenBatch createTokenBatch(@RequestBody TuitionCentre tuitionCentre) {
		return this.tokenBatchService.createTokenBatch(tuitionCentre);
	}

	
	@RequestMapping("/data/tc/saveTokenBatch")
	public @ResponseBody TokenBatch saveTokenBatch(@RequestBody TokenBatch tokenBatch) {
		this.tokenService.addTokens(tokenBatch);
		return tokenBatch;
	}

	@RequestMapping(value="/data/tc/userExists/{userName}", method = RequestMethod.GET)
	public @ResponseBody Boolean userExists(@PathVariable("userName") String userName) {
		return this.tuitionCentreService.userExists(userName);
	}
	
	/*  
	 -----------------
	 -- Token Batch --
	 -----------------
	*/	
	
	@RequestMapping("/data/tc/tokenBatch/questionnaires/eligible")
	public @ResponseBody List<Questionnaire> tuitionCentreTokenBatchEligibleQuestionnaires(@RequestBody TokenBatch tokenBatch) {
		this.temporaryEntityService.setEntity(tokenBatch);
		
		return this.questionnaireService.findAllExcept(tokenBatch.getQuestionnaire());
	}
	
	@RequestMapping(value="/data/tc/tokenBatch/set/questionnaire", method = RequestMethod.POST)
	public @ResponseBody TokenBatch setQuestionnaire(@RequestBody Questionnaire questionnaire) {
		TokenBatch tokenBatch = this.temporaryEntityService.getEntity();
		tokenBatch.setQuestionnaire(questionnaire);
		
		return this.temporaryEntityService.setEntity(tokenBatch).getEntity();
	}
	
	@RequestMapping(value="/data/tc/tokenBatch/remove/questionnaire", method = RequestMethod.POST)
	public @ResponseBody TokenBatch removeTokenBatch() {
		TokenBatch tokenBatch = this.temporaryEntityService.getEntity();
		tokenBatch.setQuestionnaire(null);
		
		return this.temporaryEntityService.setEntity(tokenBatch).getEntity();
	}

	
	
	/*  
	 ------------------
	 -- Token Search --
	 ------------------
	*/	

	@RequestMapping(value="/data/tc/token/search", method = RequestMethod.POST)
	public @ResponseBody List<EligibleToken> searchTokens(@RequestBody SearchTokenCriteria tokenCriteria) {
		return this.tokenService.searchTokens(tokenCriteria);
	}
	
	@RequestMapping(value="/data/tc/token/criteria/create", method = RequestMethod.POST)
	public @ResponseBody SearchTokenCriteria createCriteria(@RequestBody TuitionCentre tuitionCentre) {
		return this.tokenService.createSearchTokenCriteriaForLast30Days(tuitionCentre);
	}
	
	@RequestMapping(value="/data/tc/token/criteria/init", method = RequestMethod.POST)
	public @ResponseBody SearchTokenCriteria initCriteria(SearchTokenCriteria searchTokenCriteria) {
		return this.tokenService.initSearchTokenCriteria(searchTokenCriteria);
	}
	
	
	/*  
	 ----------------------
	 -- Token Management --
	 ----------------------
	*/
	@RequestMapping(value="/data/tc/token/manage/markaspayed", method = RequestMethod.POST)
	public @ResponseBody List<EligibleToken> markTokensAsPayed(@RequestBody List<EligibleToken> tokens) {
		return this.tokenService.markTokensAs(tokens, true);
	}
	
	@RequestMapping(value="/data/tc/token/manage/markasnotpayed", method = RequestMethod.POST)
	public @ResponseBody List<EligibleToken> markTokensAsNotPayed(@RequestBody List<EligibleToken> tokens) {
		return this.tokenService.markTokensAs(tokens, false);
	}

	@RequestMapping(value="/data/tc/token/manage/select", method = RequestMethod.POST)
	public @ResponseBody List<EligibleToken> selectTokens(@RequestBody List<EligibleToken> tokens) {
		return this.tokenService.setEligibleTokensAs(tokens, true);
	}
	
	@RequestMapping(value="/data/tc/token/manage/deselect", method = RequestMethod.POST)
	public @ResponseBody List<EligibleToken> deselectTokens(@RequestBody List<EligibleToken> tokens) {
		return this.tokenService.setEligibleTokensAs(tokens, false);
	}
	
	@RequestMapping(value="/data/tc/token/manage/selectpayed", method = RequestMethod.POST)
	public @ResponseBody List<EligibleToken> selectPayedTokens(@RequestBody List<EligibleToken> tokens) {
		return this.tokenService.setEligiblePayedTokensAs(tokens, true);
	}

	@RequestMapping(value="/data/tc/token/manage/deselectpayed", method = RequestMethod.POST)
	public @ResponseBody List<EligibleToken> deselectPayedTokens(@RequestBody List<EligibleToken> tokens) {
		return this.tokenService.setEligiblePayedTokensAs(tokens, false);
	}

	@RequestMapping(value="/data/tc/token/manage/selectnotpayed", method = RequestMethod.POST)
	public @ResponseBody List<EligibleToken> selectNotPayedTokens(@RequestBody List<EligibleToken> tokens) {
		return this.tokenService.setEligibleNotPayedTokensAs(tokens, true);
	}

	@RequestMapping(value="/data/tc/token/manage/deselectnotpayed", method = RequestMethod.POST)
	public @ResponseBody List<EligibleToken> deselectNotPayedTokens(@RequestBody List<EligibleToken> tokens) {
		return this.tokenService.setEligibleNotPayedTokensAs(tokens, false);
	}
	
}
