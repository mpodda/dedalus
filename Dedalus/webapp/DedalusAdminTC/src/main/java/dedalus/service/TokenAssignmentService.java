package dedalus.service;

import java.util.Date;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dedalus.domain.Pupil;
import dedalus.domain.Token;
import dedalus.domain.structures.SendTokenRequest;
import dedalus.service.api.AnswerService;
import dedalus.service.api.TokenService;
import dedalus.service.api.TokenStatusService;
import dedalus.service.api.TuitionCentreService;

@Service
public class TokenAssignmentService {
	private JavaMailSender javaMailSender;
	private TuitionCentreService tuitionCentreService; 
	private TokenService tokenService;
	private TokenStatusService tokenStatusService;
	private AnswerService answerService;
	
	@Value("${email.template.subject}")
	private String emailSubject;
	
	@Value("${email.template.applicationAddress}")
	private String emailApplicationAddress;
	
	@Value("${email.template.text}")
	private String emailText;
	
	
	
	@Autowired
	public TokenAssignmentService(JavaMailSender javaMailSender,
			TuitionCentreService tuitionCentreService,
			TokenService tokenService,
			TokenStatusService tokenStatusService,
			AnswerService answerService) {
		this.javaMailSender = javaMailSender;
		this.tuitionCentreService = tuitionCentreService;
		this.tokenService = tokenService;
		this.tokenStatusService = tokenStatusService;
		this.answerService = answerService;
	}
	
	@Transactional
	public Token sendToken(SendTokenRequest sendTokenRequest) throws MailException, MessagingException {
		Optional<Token> oToken = tuitionCentreService.getNextAvailableToken(sendTokenRequest);
		
		if (oToken.isPresent()) {
			Token token = oToken.get();
			token.setAssignDate(new Date());
			token.setPupil(sendTokenRequest.getPupil());
			token = tokenStatusService.notStarted(token);
			
			tokenService.save(token);
			answerService.prepareAnswersForToken(token);
			
//			createMimeMessage(sendTokenRequest.getPupil(), token);
//			
//			javaMailSender.send(createMimeMessage(sendTokenRequest.getPupil(), token));
		}
		
		return oToken.orElse(null); //oToken.get();
	}
	
	public SendTokenRequest createSendTokenRequest() {
		return new SendTokenRequest();
	}
	
	private MimeMessage createMimeMessage(Pupil pupil, Token token) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		if (pupil.getEmail()==null) {
			throw new RuntimeException("Pupil has no email address");
		}
		
		
        helper.setTo(pupil.getEmail());
        
        helper.setSubject(emailSubject);
        
        String questionnaireUrl = String.format("%s?token=%s", emailApplicationAddress, token.getValue());
        String emailBody = emailText.replace("{0}", questionnaireUrl);
        helper.setText(emailBody);
		
        System.out.println(String.format("questionnaireUrl = %s", questionnaireUrl));
        System.out.println(String.format("emailBody = %s", emailBody));
        
		return message;
	}
	
}
