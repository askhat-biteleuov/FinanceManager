package com.fm.internal.services;

import com.fm.internal.currency.GetCurrency;
import com.fm.internal.daos.IncomeDao;
import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;
import com.fm.internal.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ContextConfiguration("classpath:spring-utils.xml")
public class IncomeServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private IncomeDao incomeDao;

    @Autowired
    private GetCurrency getCurrency;

    private final static String USER_EMAIL = "user@email";

    private final static LocalDateTime DATE = LocalDateTime.now();

    private final static BigDecimal SUM_OF_INCOMES = BigDecimal.valueOf(1234)
            .add(BigDecimal.valueOf(111))
            .add(BigDecimal.valueOf(2222))
            .add(BigDecimal.valueOf(2222));

    @BeforeMethod
    public void setUp() throws Exception {
        getCurrency.execute();
        User user = new User(USER_EMAIL, "password", new UserInfo("name", "surname", currencyService.findCurrencyByCharCode("RUB")));
        userService.createUser(user);
        Account acc1 = new Account("visa", BigDecimal.valueOf(1234), null, userService.findByEmail(USER_EMAIL),
                currencyService.findCurrencyByCharCode("RUB"));
        accountService.createAccount(acc1);
        Income inc1 = new Income(BigDecimal.valueOf(1234), DATE.toLocalDate(), DATE.toLocalTime(), acc1);
        Income inc2 = new Income(BigDecimal.valueOf(111), DATE.toLocalDate(), DATE.toLocalTime(), acc1);
        Income inc3 = new Income(BigDecimal.valueOf(2222), DATE.toLocalDate(), DATE.toLocalTime(), acc1);
        Income inc4 = new Income(BigDecimal.valueOf(2222), DATE.toLocalDate(), DATE.toLocalTime(), acc1);
        incomeService.addIncome(inc1);
        incomeService.addIncome(inc2);
        incomeService.addIncome(inc3);
        incomeService.addIncome(inc4);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        User user = userService.findByEmail(USER_EMAIL);
        if (user != null) {
            userService.deleteUser(user);
        }
    }

    @Test
    public void testFindAllIncomesInAccount() throws Exception {
        Assert.assertEquals(incomeService.findAllIncomesInAccount
                (accountService.findUserAccountByName
                        (userService.findByEmail(USER_EMAIL), "visa")).size(), 4);
    }

    @Test
    public void testFindIncomesInAccountByDate() throws Exception {
        Account account = accountService.findUserAccountByName(userService.findByEmail(USER_EMAIL), "visa");
        LocalDate start = LocalDate.of(DATE.getYear(), DATE.getMonth().getValue()-1, DATE.getDayOfMonth());
        LocalDate end = LocalDate.of(DATE.getYear(), DATE.getMonth().getValue()+1, DATE.getDayOfMonth());
        Assert.assertEquals(incomeService.findIncomesInAccountByDate(account, start, end).size(), 4);
    }

    @Test
    public void testCascadeDeleting() throws Exception {
        userService.deleteUser(userService.findByEmail(USER_EMAIL));
        Assert.assertEquals(incomeService.findAllIncomesInAccount
                (accountService.findUserAccountByName
                        (userService.findByEmail(USER_EMAIL), "visa")).size(), 0);
    }

    @Test
    public void testSumOfAllIncomes() throws Exception {
        List<Income> allIncomesInAccount = incomeService.findAllIncomesInAccount(accountService.findAccountById(2));
        BigDecimal sumOfAllIncomes = incomeService.sumOfAllIncomes(allIncomesInAccount);
        Assert.assertNotNull(sumOfAllIncomes);
        Assert.assertEquals(sumOfAllIncomes.doubleValue(), SUM_OF_INCOMES.doubleValue());
    }

    @Test
    public void testSumOfAllIncomesByDao() throws Exception {
        BigDecimal sumOfIncomesInAccount = incomeDao.getSumOfIncomesInAccount(accountService.findAccountById(2));
        Assert.assertNotNull(sumOfIncomesInAccount);
        Assert.assertEquals(sumOfIncomesInAccount.doubleValue(), SUM_OF_INCOMES.doubleValue());
    }

}