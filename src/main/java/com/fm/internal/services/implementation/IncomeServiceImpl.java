package com.fm.internal.services.implementation;

import com.fm.internal.daos.IncomeDao;
import com.fm.internal.dtos.IncomeDto;
import com.fm.internal.models.*;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.HashTagService;
import com.fm.internal.services.IncomeService;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeDao dao;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UtilServiceImpl utilService;
    @Autowired
    private HashTagService hashTagService;

    @Override
    public void addIncome(Income income) {
        dao.add(income);
        income.getAccount().setBalance(getBalanceAfterIncomeOperation(income));
        accountService.updateAccount(income.getAccount());
    }

    @Override
    public void updateIncome(Income income) {
        dao.update(income);
    }

    @Override
    public void deleteIncome(Income income) {
        dao.delete(income);
        income.getAccount().setBalance(getBalanceAfterIncomeOperation(income));
        accountService.updateAccount(income.getAccount());
    }

    private BigDecimal getBalanceAfterIncomeOperation(Income income) {
        Account account = income.getAccount();
        return utilService.recountAccountBalance(account);
    }

    @Override
    public Income findById(long id) {
        return dao.getById(id);
    }

    @Override
    public List<Income> findAllIncomesInAccount(Account account) {
        return dao.getAccountIncomes(account);
    }

    @Override
    public List<Income> findIncomesInAccountByDate(Account account, LocalDate start, LocalDate end) {
        return dao.getAccountIncomesByDate(account, start, end);
    }

    @Override
    public List<Income> getAccountIncomesPageByDate(Account account, int first, int limit, LocalDate start, LocalDate end) {
        return dao.getAccountIncomesPageByDate(account, first, limit, start, end);
    }

    @Override
    public List<Income> getPageOfIncomes(Account account, int first, int limit) {
        return dao.getIncomesPage(account, first, limit);
    }

    @Override
    public List<Income> getUserIncomesPageByDate(User user, int first, int limit, LocalDate start, LocalDate end) {
        return dao.getUserIncomesPageByDate(user, first, limit, start, end);
    }

    @Override
    public Long getUserIncomesNumberByDate(User user, LocalDate start, LocalDate end) {
        return dao.getUserIncomesNumberByDate(user, start, end);
    }

    @Override
    public Long getAccountIncomesNumberByDate(Account account, LocalDate start, LocalDate end) {
        return dao.getAccountIncomeNumberByDate(account, start, end);
    }

    @Override
    public Income createIncomeFromDto(IncomeDto incomeDto) {
        User user = userService.getLoggedUser();
        Account account = accountService.findAccountById(incomeDto.getAccountId());
        Income income = new Income();
        income.setAccount(account);
        income.setNote(incomeDto.getNote());
        income.setAmount(new BigDecimal(incomeDto.getAmount()));
        income.setDate(LocalDate.parse(incomeDto.getDate()));
        income.setTime(LocalTime.now());
//        List<HashTag> hashtags = new ArrayList<>();
//        if(!incomeDto.getHashTags().isEmpty()){
//            for(String hashtag: incomeDto.getHashTags()){
//                HashTag tag = hashTagService.getHashTagByUserAndText(user,hashtag);
//                if(tag==null && hashtag.trim().length()>0) {
//                    hashTagService.addHashTag(new HashTag(hashtag,user));
//                    hashtags.add(hashTagService.getHashTagByUserAndText(user,hashtag));
//                }else{
//                    hashtags.add(tag);
//                }
//            }
//        }
        income.setHashTags(utilService.parseHashTags(user, incomeDto.getHashTags()));
        return income;
    }

    @Override
    public BigDecimal sumOfAllIncomes(List<Income> incomes) {
        if (incomes.size() == 0) {
            return BigDecimal.ZERO;
        }
        return incomes.stream().map(Income::getAmount).reduce(BigDecimal::add).get();
    }

    @Override
    public List<Income> getIncomesByAccountAndHashTag(Account account, HashTag hashTag, LocalDate start, LocalDate end) {
        return dao.getIncomesByAccountAndHashTag(account, hashTag, start, end);
    }

    @Override
    public List<Income> getAccountIncomesPageByHashTagAndDate(Account account, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end) {
        return dao.getAccountIncomesPageByHashTagAndDate(account, hashTag, offset, limit, start, end);
    }

    @Override
    public List<Income> getIncomesByUserAndHashTag(User user, HashTag hashTag, LocalDate start, LocalDate end) {
        return dao.getIncomesByUserAndHashTag(user, hashTag, start, end);
    }

    @Override
    public List<Income> getUserIncomesPageByHashTagAndDate(User user, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end) {
        return dao.getUserIncomesPageByHashTagAndDate(user, hashTag, offset, limit, start, end);
    }
}
