package dedalus.cotroller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Pupil;
import dedalus.domain.Questionnaire;
import dedalus.domain.Token;
import dedalus.domain.structures.SendTokenRequest;
import dedalus.service.TokenAssignmentService;
import dedalus.service.UserSessionService;
import dedalus.service.api.PupilService;

@Controller
public class PupilsController extends BaseController {
	private PupilService pupilService;
	private UserSessionService userSessionService;
	private TokenAssignmentService tokenAssignmentService;
	
	
	@Autowired
	public PupilsController(PupilService pupilService, UserSessionService userSessionService, TokenAssignmentService tokenAssignmentService) {
		super(userSessionService);
		this.pupilService = pupilService;
		this.userSessionService = userSessionService;
		this.tokenAssignmentService = tokenAssignmentService;
	}

	@RequestMapping("/app/pupils")
	@Override
	public String loadPage(Model model) {
		return super.loadPage(model);
	}
	
	@RequestMapping("/data/pupils")
	public @ResponseBody List<Pupil> getPupils() {
		return pupilService.byTuitionCentre(userSessionService.getUser().getTuitionCentre().getId());
	}
	
	@RequestMapping(value="/data/pupil/create", method = RequestMethod.POST)
	public @ResponseBody Pupil createPupil() {
		return this.pupilService.create(userSessionService.getUser().getTuitionCentre());
	}
	
	@RequestMapping(value="/data/pupil/save", method = RequestMethod.POST)
	public @ResponseBody Pupil savePupil(@RequestBody Pupil pupil) {
		return this.pupilService.save(pupil);
	}
	
	@RequestMapping(value="/data/pupil/delete", method = RequestMethod.POST)
	public @ResponseBody Pupil deletePupil(@RequestBody Pupil pupil) {
		this.pupilService.delete(pupil);
		
		return pupil;
	}
	
	@RequestMapping(value="/data/pupil/get", method = RequestMethod.POST)
	public @ResponseBody Pupil getPupil(@RequestBody Pupil pupil) {
		return this.pupilService.get(pupil);
	}

	@RequestMapping(value="/data/pupil/newToken", method = RequestMethod.POST)
	public @ResponseBody Token getNewToken(@RequestBody Pupil pupil) {
		return this.pupilService.newToken(pupil);
	}

	@RequestMapping(value="/data/pupil/tokens", method = RequestMethod.POST)
	public @ResponseBody List<Token> getTokens(@RequestBody Pupil pupil) {
		return this.pupilService.getTokens(pupil);
	}
	
	@RequestMapping(value="/data/pupil/sendToken/create", method = RequestMethod.GET)
	public @ResponseBody SendTokenRequest createSendTokenRequest() {
		return this.tokenAssignmentService.createSendTokenRequest();
	}
	
	@RequestMapping(value="/data/pupil/sendToken", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Token> sendToken(@RequestBody SendTokenRequest sendTokenRequest) {
		
		ResponseEntity<Token> responseEntity;
		try {
			responseEntity = new ResponseEntity<Token>(this.tokenAssignmentService.sendToken(sendTokenRequest), HttpStatus.OK);
		} catch (MailException | MessagingException e) {
			e.printStackTrace();
			
			responseEntity = new ResponseEntity<Token>(HttpStatus.BAD_REQUEST);
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/data/pupil/availableQuestionnaires", method = RequestMethod.POST)
	public @ResponseBody List<Questionnaire> getAvailableQuestionnaires(@RequestBody Pupil pupil) {
		return this.pupilService.getAvailableQuestionnaires(pupil);
	}

	@Override
	public String requestedPageName() {
		return PageName.Pupils.getName();
	}

	@Override
	public String pagePath() {
		return "/app/pupils";
	}
}
