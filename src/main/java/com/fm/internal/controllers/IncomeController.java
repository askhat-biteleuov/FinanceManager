package com.fm.internal.controllers;

import com.fm.internal.dtos.IncomeDto;
import com.fm.internal.dtos.PaginationDto;
import com.fm.internal.dtos.RangeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.HashTag;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.IncomeService;
import com.fm.internal.services.StatusBarService;
import com.fm.internal.services.UserService;
import com.fm.internal.services.implementation.PaginationServiceImpl;
import com.fm.internal.services.RangeService;
import com.fm.internal.validation.util.ValidErrors;
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
@RequestMapping("/income")
public class IncomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private IncomeService incomeService;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addIncome(@Valid @RequestBody IncomeDto incomeDto, BindingResult result) {
        User user = userService.getLoggedUser();
        if (result.hasErrors() || user == null) {
            Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        incomeService.addIncome(incomeService.createIncomeFromDto(incomeDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView userIncomes(@RequestParam(value = "accountId", required = false) Long accountId,
                                    @RequestParam(value = "pageId", required = false) Integer pageId,
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
        ModelAndView modelAndView = new ModelAndView("incomes-list");
        User user = userService.getLoggedUser();
        if (hashTag == null){
            if (accountId == null){
                addUserIncomesPageToView(user, pageId, start, end, modelAndView);
            } else if(accountId != null){
                addAccountIncomesPageToView(accountId, pageId, start, end, modelAndView);
            }
        } else if (!hashTag.isEmpty()){
            HashTag searchHashTag = new HashTag(hashTag, user);
            if (accountId == null){
                addUserIncomesPageToView(pageId, start, end, modelAndView, user, searchHashTag);
            } else if (accountId != null){
                addAccountIncomesPageInView(accountId, pageId, start, end, modelAndView, searchHashTag);
            }
            modelAndView.addObject("hashTag", hashTag);
        }
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(user));
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    private void addAccountIncomesPageInView(Long accountId, Integer pageId,
                                              LocalDate start, LocalDate end, ModelAndView modelAndView,
                                              HashTag searchHashTag) {
        Account accountById = accountService.findAccountById(accountId);
        long incomesAmount = incomeService.getIncomesByAccountAndHashTag(accountById, searchHashTag,
                start, end).size();
        PaginationDto paginationDto = paginationService.createPagination(accountId, pageId, PAGE_SIZE,
                incomesAmount, "/income/all");
        List<Income> outcomesPage = incomeService.getAccountIncomesPageByHashTagAndDate(accountById, searchHashTag,
                paginationDto.getFirstItem(), PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("incomes", outcomesPage);
        modelAndView.addObject("accountId", accountId);
    }

    private void addUserIncomesPageToView(Integer pageId, LocalDate start,
                                           LocalDate end, ModelAndView modelAndView, User user,
                                           HashTag searchHashTag) {
        long userIncomesAmount = incomeService.getIncomesByUserAndHashTag(user, searchHashTag,
                start, end).size();
        PaginationDto paginationDto = paginationService.createPagination(user.getId(), pageId, PAGE_SIZE,
                userIncomesAmount, "/income/all");
        List<Income> incomesPage = incomeService.getUserIncomesPageByHashTagAndDate(user, searchHashTag,
                paginationDto.getFirstItem(), PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("incomes", incomesPage);
    }

    private void addAccountIncomesPageToView(Long accountId, Integer pageId,
                                              LocalDate start, LocalDate end, ModelAndView modelAndView) {
        Account accountById = accountService.findAccountById(accountId);
        long incomesAmount = incomeService.getAccountIncomesNumberByDate(accountById, start, end);
        PaginationDto paginationDto = paginationService.createPagination(accountId, pageId, PAGE_SIZE,
                incomesAmount, "/income/all");
        List<Income> incomesPage = incomeService.getAccountIncomesPageByDate(accountById,
                paginationDto.getFirstItem(), PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("incomes", incomesPage);
        modelAndView.addObject("accountId", accountId);
    }

    private void addUserIncomesPageToView(User user, Integer pageId,
                                           LocalDate start, LocalDate end, ModelAndView modelAndView) {
        Long userIncomesAmount = incomeService.getUserIncomesNumberByDate(user, start, end);
        PaginationDto paginationDto = paginationService.createPagination(user.getId(), pageId, PAGE_SIZE,
                userIncomesAmount, "/income/all");
        List<Income> incomesPage = incomeService.getUserIncomesPageByDate(user, paginationDto.getFirstItem(),
                PAGE_SIZE, start, end);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("incomes", incomesPage);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteIncomeById(@RequestParam("incomeId") Long incomeId,
                                         final HttpServletRequest request) {
        incomeService.deleteIncome(incomeService.findById(incomeId));
        String referer = request.getHeader("referer");
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateIncome(@RequestBody IncomeDto incomeDto) {
        Income incomeById = incomeService.findById(incomeDto.getIncomeId());
        if (incomeById != null) {
            incomeById.setNote(incomeDto.getNote());
            incomeService.updateIncome(incomeById);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

