package dedalus.service.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Pupil;
import dedalus.domain.Questionnaire;
import dedalus.domain.Token;
import dedalus.domain.TuitionCentre;
import dedalus.repository.AbstractJpaDao;
import dedalus.repository.PupilRepository;

@Service
public class PupilService extends IdentifiableEntityService<Pupil> {
	private PupilRepository pupilRepository;
	private TokenService tokenService;
	private TuitionCentreService tuitionCentreService;
	
	@Autowired
	public PupilService(PupilRepository pupilRepository, TokenService tokenService, TuitionCentreService tuitionCentreService) {
		this.pupilRepository = pupilRepository;
		this.tokenService = tokenService;
		this.tuitionCentreService = tuitionCentreService;
	}

	@Override
	public AbstractJpaDao<Pupil> getRepository() {
		return pupilRepository;
	}
	
	public List<Pupil> byTuitionCentre(Long tuitionCentreId) {
		return this.pupilRepository.byTuitionCentre(tuitionCentreId);
	}
	
	public Pupil create(TuitionCentre tuitionCentre) {
//		Pupil pupil = this.create();
//		pupil.setTuitionCentre(tuitionCentre);
		
		return this.create().tuitionCentre(tuitionCentre);
	}

	public Token newToken(Pupil pupil) {
		List<Token> availableTokens = tokenService.byTuitionCentre(pupil.getTuitionCentre(), true);

		if (availableTokens != null && !availableTokens.isEmpty()) {
			return availableTokens.get(0);
		}

		return null;
	}

	public List<Token> getTokens(Pupil pupil) {
		return tokenService.byPupil(pupil);
	}
	
	public List<Questionnaire> getAvailableQuestionnaires(Pupil pupil) {
		Set<Questionnaire> questionnaires = new HashSet<Questionnaire>();
		
		List<Token> tokens = this.tuitionCentreService.getTokens(pupil.getTuitionCentre()).stream().filter (
				t-> t.isAvailable()
				).collect(Collectors.toList());
		
		tokens.forEach (
			token -> {
				if (token.getQuestionnaire() != null) {
					questionnaires.add(token.getQuestionnaire());
				}
			}
		);
		
		return new ArrayList<Questionnaire>(questionnaires);
	}
}
