package dedalus.cotroller;

import dedalus.domain.Token;
import dedalus.domain.search.SearchTokenCriteria;
import dedalus.enums.TokenStatus;
import dedalus.service.UserSessionService;
import dedalus.service.api.TuitionCentreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TokensController extends BaseController {
    private TuitionCentreService tuitionCentreService;

    @Autowired
    public TokensController(UserSessionService userSessionService, TuitionCentreService tuitionCentreService) {
        super(userSessionService);
        this.tuitionCentreService = tuitionCentreService;
    }

    @Override
    @RequestMapping("/app/tokens")
    public String loadPage(Model model) {
        return super.loadPage(model);
    }

    @RequestMapping("/data/tokens/available")
    public int availableTokens() {
        return tuitionCentreService.countTokensByStatus(userSessionService.getUser().getTuitionCentre(), TokenStatus.Available.getStatus());
    }
    
    @RequestMapping("/data/tokens/notStarted")
    public int notStartedTokens() {
        return tuitionCentreService.countTokensByStatus(userSessionService.getUser().getTuitionCentre(), TokenStatus.NotStarted.getStatus());
    }

    @RequestMapping("/data/tokens/onGoing")
    public int onGoingTokens() {
        return tuitionCentreService.countTokensByStatus(userSessionService.getUser().getTuitionCentre(), TokenStatus.OnGoing.getStatus());
    }

    @RequestMapping("/data/tokens/completed")
    public int completedTokens() {
        return tuitionCentreService.countTokensByStatus(userSessionService.getUser().getTuitionCentre(), TokenStatus.Completed.getStatus());
    }

    
//    @RequestMapping("/data/tokens/unused")
//    public int notUsedTokens() {
//        return tuitionCentreService.countAvailableTokens(userSessionService.getUser().getTuitionCentre());
//    }
//
//    @RequestMapping("/data/tokens/notAnswered")
//    public int notAnsweredTokens() {
//        return tuitionCentreService.countNotAnsweredTokens(userSessionService.getUser().getTuitionCentre());
//    }

    @RequestMapping("/data/tokens/history/criteria/create")
    public SearchTokenCriteria createSearchTokenCriteria() {
        return tuitionCentreService.createSearchTokenCriteria(userSessionService.getUser().getTuitionCentre());
    }

    @RequestMapping("/data/tokens/history")
    public List<Token> tokensHistory(SearchTokenCriteria tokenCriteria){
        return tuitionCentreService.tokensHistory(tokenCriteria);
    }

    
    @Override
    public String requestedPageName() {
        return PageName.Tokens.getName();
    }

    @Override
    public String pagePath() {
        return "/app/tokens";
    }
}