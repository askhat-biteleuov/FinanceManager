package com.fm.internal.controllers;

import com.fm.internal.dtos.GoalDto;
import com.fm.internal.dtos.PaginationDto;
import com.fm.internal.dtos.RangeDto;
import com.fm.internal.models.Goal;
import com.fm.internal.models.Income;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.User;
import com.fm.internal.services.*;
import com.fm.internal.services.implementation.PaginationServiceImpl;
import com.fm.internal.validation.GoalValidator;
import com.fm.internal.validation.util.ValidErrors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/goal")
public class GoalController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);


    @Autowired
    private UserService userService;
    @Autowired
    private StatusBarService statusBarService;
    @Autowired
    private HashTagService hashTagService;
    @Autowired
    private GoalService goalService;
    @Autowired
    private GoalValidator goalValidator;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private RangeService rangeService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private PaginationServiceImpl paginationService;
    @Autowired
    private AccountService accountService;

    final int PAGE_SIZE = 10;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getGoal(@RequestParam(required = false) Long goalId) {
        User loggedUser = userService.getLoggedUser();
        ModelAndView modelAndView = new ModelAndView();
        if (goalId != null) {
            //show goal(outcomes, incomes)
            modelAndView.setViewName("goal");
        } else {
            modelAndView.addObject("goalsMessages", goalService.getGoalsWithoutIncomeForMonth(loggedUser));
            modelAndView.addObject("currencies", currencyService.getCurrencies());
            List<Goal> userGoals = goalService.getGoalsByUser(loggedUser);
            userGoals.forEach(goal -> {
                if (goal.getDate().isAfter(LocalDate.now()) && !goal.isOverdue()) {
                    goal.setOverdue(true);
                }
            });
            modelAndView.addObject("goals", userGoals);
            modelAndView.setViewName("goal-list");
        }
        modelAndView.addObject("accounts", accountService.findAllUserAccounts(loggedUser));
        modelAndView.addObject("hashtags", hashTagService.getHashTagsByUser(loggedUser));
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(loggedUser));
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addGoal(@Valid @RequestBody GoalDto goalDto, BindingResult result) {
        goalValidator.validate(goalDto, result);
        User loggedUser = userService.getLoggedUser();
        if (result.hasErrors() || loggedUser == null) {
            Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
            return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        goalService.addGoal(goalDto, loggedUser);
        LOGGER.info("New account was added:" + goalDto.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/page/incomes", method = RequestMethod.GET)
    public ModelAndView goalIncomesPage(@RequestParam(value = "goalId") Long goalId,
                                 @RequestParam(value = "pageId", required = false) Integer pageId,
                                 @RequestParam(value = "start", required = false) String startFromUrl,
                                 @RequestParam(value = "end", required = false) String endFromUrl,
                                 @ModelAttribute("rangeDto") RangeDto rangeDto) {
        User loggedUser = userService.getLoggedUser();
        if (pageId == null) {
            pageId = 1;
        }
        LocalDate start;
        LocalDate end;
        if (startFromUrl != null && endFromUrl != null){
            RangeDto datesFromUrlDto = new RangeDto();
            datesFromUrlDto.setStart(startFromUrl);
            datesFromUrlDto.setEnd(endFromUrl);
            start = rangeService.setupStart(datesFromUrlDto);
            end = rangeService.setupEnd(datesFromUrlDto);
        } else {
            start = rangeService.setupStart(rangeDto);
            end = rangeService.setupEnd(rangeDto);
        }
        ModelAndView modelAndView = new ModelAndView("goal-incomes-page");
        Goal goalById = goalService.getGoalById(goalId);
        long incomesAmount = incomeService.getAccountIncomesNumberByDate(goalById, start, end);
        PaginationDto paginationDto = paginationService.createPagination(goalId, pageId, PAGE_SIZE,
                incomesAmount, "/goal/page/incomes");
        List<Income> incomesPage = incomeService.getAccountIncomesPageByDate(goalById,
                paginationDto.getFirstItem(), PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("incomes", incomesPage);
        modelAndView.addObject("goalId", goalId);
        modelAndView.addObject("goal", goalById);
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(loggedUser));
        return modelAndView;
    }

    @RequestMapping(value="/page/outcomes", method = RequestMethod.GET)
    public ModelAndView goalOutcomesPage(@RequestParam(value = "goalId") Long goalId,
                                 @RequestParam(value = "pageId", required = false) Integer pageId,
                                 @RequestParam(value = "start", required = false) String startFromUrl,
                                 @RequestParam(value = "end", required = false) String endFromUrl,
                                 @ModelAttribute("rangeDto") RangeDto rangeDto) {
        User loggedUser = userService.getLoggedUser();
        if (pageId == null) {
            pageId = 1;
        }
        LocalDate start;
        LocalDate end;
        if (startFromUrl != null && endFromUrl != null){
            RangeDto datesFromUrlDto = new RangeDto();
            datesFromUrlDto.setStart(startFromUrl);
            datesFromUrlDto.setEnd(endFromUrl);
            start = rangeService.setupStart(datesFromUrlDto);
            end = rangeService.setupEnd(datesFromUrlDto);
        } else {
            start = rangeService.setupStart(rangeDto);
            end = rangeService.setupEnd(rangeDto);
        }
        ModelAndView modelAndView = new ModelAndView("goal-outcomes-page");
        Goal goalById = goalService.getGoalById(goalId);
        long outcomesAmount = outcomeService.getAccountOutcomesNumberByDate(goalById, start, end);
        PaginationDto paginationDto = paginationService.createPagination(goalId, pageId, PAGE_SIZE,
                outcomesAmount, "/goal/page/outcomes");
        List<Outcome> outcomesPage = outcomeService.getAccountOutcomesPageByDate(goalById,
                paginationDto.getFirstItem(), PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("outcomes", outcomesPage);
        modelAndView.addObject("goalId", goalId);
        modelAndView.addObject("goal", goalById);
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(loggedUser));
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object editGoal(@Valid @RequestBody GoalDto goalDto, BindingResult result) {
        Goal goal = goalService.getGoalById(goalDto.getId());
        goalValidator.validate(goalDto, result);
        if (result.hasErrors()) {
            Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
            return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        goal.setName(goalDto.getName().trim());
        goal.setGoalAmount(new BigDecimal(goalDto.getGoalAmount().trim()));
        goal.setDate(LocalDate.parse(goalDto.getDate()));
        goalService.updateGoal(goal);
        LOGGER.info("Goal with id:" + goalDto.getId() + " was updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
