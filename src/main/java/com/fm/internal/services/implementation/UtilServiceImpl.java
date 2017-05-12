package com.fm.internal.services.implementation;

import com.fm.internal.models.*;
import com.fm.internal.services.IncomeService;
import com.fm.internal.services.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilServiceImpl {
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private IncomeService incomeService;

    public BigDecimal recountAccountBalance(Account account) {
        List<Outcome> allOutcomesInAccount = outcomeService.findAllOutcomesInAccount(account);
        List<Income> allIncomesInAccount = incomeService.findAllIncomesInAccount(account);
        BigDecimal sumOfIncomes = incomeService.sumOfAllIncomes(allIncomesInAccount);
        BigDecimal sumOfOutcomes = outcomeService.sumOfAllOutcomes(allOutcomesInAccount);
        return sumOfIncomes.subtract(sumOfOutcomes);
    }

    public List<HashTag> parseHashTags(User user, String hashTags) {
        Pattern hashTagPattern = Pattern.compile("(^|\\s)#([^#\\s]+)");//(^|\s)#[^#\s](\S+)
        Matcher hashTagMatcher = hashTagPattern.matcher(hashTags);
        List<HashTag> hashTagsList = new ArrayList<>();
        while (hashTagMatcher.find()) {
            hashTagsList.add(new HashTag(hashTagMatcher.group().trim(), user));
        }
        return hashTagsList;
    }
}
