package com.epam.internal.services.implementation;

import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.IncomeService;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = {"file:../webapp/"})
public class IncomeServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    private User user;
    private Account account;
    private Income income;

    @BeforeClass(dependsOnMethods = {"springTestContextPrepareTestInstance"})
    public void setUp() {
        user = new User("email", "password", new UserInfo("firstName", "lastName"));
        userService.createUser(user);
        account = new Account("creditCard", BigDecimal.valueOf(11111), null, user);
        accountService.createAccount(account);
        income = new Income(BigDecimal.valueOf(40000), new Date(), account);
        incomeService.addIncome(income);
    }

    @Test(enabled = false)
    public void testAddIncome() throws Exception {
        incomeService.addIncome(income);
    }

    @Test(enabled = false)
    public void testUpdateIncome() throws Exception {
        income = new Income(BigDecimal.valueOf(50000), new Date(), account);
        incomeService.updateIncome(income);
    }

    @Test(enabled = false)
    public void testDeleteIncome() throws Exception {
    }

    @Test
    public void testFindById() throws Exception {
        Income byId = incomeService.findById(1L);
        Assert.assertNotNull(byId);
    }

    @Test
    public void testFindAllIncomesInAccount() throws Exception {
        List<Income> allIncomesInAccount = incomeService.findAllIncomesInAccount(account);
        Assert.assertEquals(allIncomesInAccount.size(), 1);
    }

}