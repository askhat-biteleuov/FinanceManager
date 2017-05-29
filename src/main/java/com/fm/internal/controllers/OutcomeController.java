package com.fm.internal.controllers;

import com.fm.internal.dtos.OutcomeDto;
import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.dtos.PaginationDto;
import com.fm.internal.dtos.RangeDto;
import com.fm.internal.models.*;
import com.fm.internal.services.*;
import com.fm.internal.services.implementation.PaginationServiceImpl;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/outcome")
public class OutcomeController {
    private static final Logger LOGGER = Logger.getLogger(OutcomeController.class);

    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private PaginationServiceImpl paginationService;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;
    @Autowired
    private StatusBarService statusBarService;
    @Autowired
    private RangeService rangeService;

    final int PAGE_SIZE = 10;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public Object addOutcome(@RequestParam long accountId) {
        Account account = accountService.findAccountById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addOutcome(@Valid @RequestBody OutcomeDto outcomeDto,
                                  BindingResult result) {
        User user = userService.getLoggedUser();
        if (result.hasErrors() || user == null) {
            Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        outcomeService.addOutcome(outcomeService.createOutcomeFromDto(outcomeDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView userOutcomes(@RequestParam(value = "accountId", required = false) Long accountId,
                                     @RequestParam(value = "pageId", required = false) Integer pageId,
                                     @RequestParam(value = "outcomeTypeId", required = false) Integer outcomeTypeId,
                                     @RequestParam(value = "hashTag", required = false) String hashTag,
                                     @RequestParam(value = "start", required = false) String startFromUrl,
                                     @RequestParam(value = "end", required = false) String endFromUrl,
                                     @ModelAttribute("rangeDto") RangeDto rangeDto) {

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
        ModelAndView modelAndView = new ModelAndView("outcomes-list");
        User user = userService.getLoggedUser();
        if (hashTag == null){
            if (accountId == null && outcomeTypeId == null){
                addUserOutcomesPageToView(user, pageId, start, end, modelAndView);
            } else if(accountId != null){
                addAccountOutcomesPageToView(accountId, pageId, start, end, modelAndView);
            } else if(outcomeTypeId != null){
                addOutcomeTypeOutcomesPageToView(outcomeTypeId, pageId, start, end, modelAndView);
            }
        } else if (!hashTag.isEmpty()){
            HashTag searchHashTag = new HashTag(hashTag, user);
            if (accountId == null && outcomeTypeId == null){
                addUserOutcomesPageToView(pageId, start, end, modelAndView, user, searchHashTag);
            } else if (accountId != null){
                addAccountOutcomesPageInView(accountId, pageId, start, end, modelAndView, searchHashTag);
            } else if(outcomeTypeId != null){
                addOutcomeTypeOutcomesPageToView(outcomeTypeId, pageId, start, end, modelAndView, searchHashTag);
            }
            modelAndView.addObject("hashTag", hashTag);
        }
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(user));
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    private void addAccountOutcomesPageInView(Long accountId, Integer pageId,
                                              LocalDate start, LocalDate end, ModelAndView modelAndView,
                                              HashTag searchHashTag) {
        Account accountById = accountService.findAccountById(accountId);
        long outcomesAmount = outcomeService.getOutcomesByAccountAndHashTag(accountById, searchHashTag,
                start, end).size();
        PaginationDto paginationDto = paginationService.createPagination(accountId, pageId, PAGE_SIZE,
                outcomesAmount, "/outcome/all");
        List<Outcome> outcomesPage = outcomeService.getAccountOutcomesPageByHashTagAndDate(accountById, searchHashTag,
                paginationDto.getFirstItem(), PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("outcomes", outcomesPage);
        modelAndView.addObject("accountId", accountId);
    }

    private void addUserOutcomesPageToView(Integer pageId, LocalDate start,
                                           LocalDate end, ModelAndView modelAndView, User user,
                                           HashTag searchHashTag) {
        long userOutcomesAmount = outcomeService.getOutcomesByUserAndHashTag(user, searchHashTag,
                start, end).size();
        PaginationDto paginationDto = paginationService.createPagination(user.getId(), pageId, PAGE_SIZE,
                userOutcomesAmount, "/outcome/all");
        List<Outcome> outcomesPage = outcomeService.getUserOutcomesPageByHashTagAndDate(user, searchHashTag,
                paginationDto.getFirstItem(), PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("outcomes", outcomesPage);
    }

    private void addOutcomeTypeOutcomesPageToView(Integer outcomeTypeId, Integer pageId,
                                                  LocalDate start, LocalDate end, ModelAndView modelAndView,
                                                  HashTag searchHashTag) {
        OutcomeType outcomeType = outcomeTypeService.findTypeById(outcomeTypeId);
        long typeOutcomesAmount = outcomeService.getOutcomesByTypeAndHashTag(outcomeType, searchHashTag,
                start, end).size();
        PaginationDto paginationDto = paginationService.createPagination(outcomeTypeId, pageId, PAGE_SIZE,
                typeOutcomesAmount, "/outcome/all");
        List<Outcome> outcomesPage = outcomeService.getTypeOutcomesPageByHashTagAndDate(outcomeType, searchHashTag,
                paginationDto.getFirstItem(), PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("outcomes", outcomesPage);
        modelAndView.addObject("outcomeTypeId", outcomeTypeId);
        modelAndView.addObject("outcomeTypeDto", new OutcomeTypeDto(outcomeTypeId, outcomeType.getName(),
                outcomeType.getLimit().toString(), outcomesPage));
    }

    private void addOutcomeTypeOutcomesPageToView(Integer outcomeTypeId, Integer pageId,
                                                  LocalDate start, LocalDate end, ModelAndView modelAndView) {
        OutcomeType outcomeType = outcomeTypeService.findTypeById(outcomeTypeId);
        long typeOutcomesAmount = outcomeTypeService.getSizeOutcomesOfTypeByDate(outcomeType, start, end);
        PaginationDto paginationDto = paginationService.createPagination(outcomeTypeId, pageId, PAGE_SIZE,
                typeOutcomesAmount, "/outcome/all");
        List<Outcome> outcomesPage = outcomeTypeService.getOutcomesOfTypeByDate(outcomeType, paginationDto.getFirstItem(),
                PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("outcomes", outcomesPage);
        modelAndView.addObject("outcomeTypeId", outcomeTypeId);
        modelAndView.addObject("outcomeTypeDto", new OutcomeTypeDto(outcomeTypeId, outcomeType.getName(),
                outcomeType.getLimit().toString(), outcomesPage));
    }

    private void addAccountOutcomesPageToView(Long accountId, Integer pageId,
                                              LocalDate start, LocalDate end, ModelAndView modelAndView) {
        Account accountById = accountService.findAccountById(accountId);
        long accountOutcomesAmount = outcomeService.getAccountOutcomesNumberByDate(accountById, start, end);
        PaginationDto paginationDto = paginationService.createPagination(accountId, pageId, PAGE_SIZE,
                accountOutcomesAmount, "/outcome/all");
        List<Outcome> outcomesPage = outcomeService.getAccountOutcomesPageByDate(accountById,
                paginationDto.getFirstItem(), PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("outcomes", outcomesPage);
        modelAndView.addObject("accountId", accountId);
    }

    private void addUserOutcomesPageToView(User user, Integer pageId,
                                           LocalDate start, LocalDate end, ModelAndView modelAndView) {
        Long userOutcomesAmount = outcomeService.getUserOutcomesNumberByDate(user, start, end);
        PaginationDto paginationDto = paginationService.createPagination(user.getId(), pageId, PAGE_SIZE,
                userOutcomesAmount, "/outcome/all");
        List<Outcome> outcomesPage = outcomeService.getUserOutcomesPageByDate(user, paginationDto.getFirstItem(),
                PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("outcomes", outcomesPage);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteOutcome(Long outcomeId, final HttpServletRequest request) {
        Outcome outcome = outcomeService.findById(outcomeId);
        outcomeService.deleteOutcome(outcome);
        String referer = request.getHeader("referer");
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateOutcome(@RequestBody OutcomeDto outcomeDto) {
        Outcome outcome = outcomeService.findById(outcomeDto.getOutcomeId());
        if (outcome != null) {
            outcome.setNote(outcomeDto.getNote());
            outcomeService.updateOutcome(outcome);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
