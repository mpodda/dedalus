package dedalus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import dedalus.service.api.AnswerService;
import dedalus.service.api.TokenStatusService;


//@Service
@Deprecated
public class DedalusAdminTuitionCentreService {

    private JavaMailSender javaMailSender;
    private TokenStatusService tokenStatusService;
    private AnswerService answerService;

    @Autowired
    public DedalusAdminTuitionCentreService(JavaMailSender javaMailSender, TokenStatusService tokenStatusService, AnswerService answerService) {
        this.javaMailSender = javaMailSender;
        this.tokenStatusService = tokenStatusService;
        this.answerService = answerService;
    }


    /*
    public void assignTokenAndSendMail(Token token, Pupil pupil) throws MessagingException {
        token.setPupil(pupil);
        token.setAssignDate(new Date());
        token = tokenStatusService.notStarted(token);
        answerService.prepareAnswersForToken(token);

        //TODO: Implement send token
        sendEmail(token);
    }
    */

    /*
    private void sendEmail(Token token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(token.getPupil().getEmail());
        helper.setSubject(token.getQuestionnaire().getDescription());
        helper.setText(String.format("<a href=''>Πάτησε εδώ</a>", token.getValue()));

        javaMailSender.send(message);
    }
    */

}