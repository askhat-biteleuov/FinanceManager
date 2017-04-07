package com.epam.internal.services.implementation;

import com.epam.internal.daos.IncomeDao;
import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.IncomeService;
import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class IncomeServiceImplTest {
    @Mock
    private IncomeDao incomeDao;

    @InjectMocks
    private IncomeService incomeService = new IncomeServiceImpl();

    @Spy
    private List<Income> incomes = new ArrayList<>();

    @Captor
    private ArgumentCaptor<Income> captor;

    private User user = new User("email", "pass", new UserInfo("firstName", "lastName"));
    private Account account = new Account("visa", BigDecimal.valueOf(5000), null, user);

    @BeforeClass
    public void setUpClass() {
        MockitoAnnotations.initMocks(this);
        incomes = getIncomeList();
    }

    @Test
    public void testAddIncome() throws Exception {
        doNothing().when(incomeDao).create(any(Income.class));
        incomeService.addIncome(incomes.get(0));

        verify(incomeDao, times(1)).create(captor.capture());

        Assert.assertEquals(captor.getValue().getAmount(), BigDecimal.valueOf(300));
        Assert.assertEquals(2, incomes.size());
        verify(incomes, times(2)).add(any(Income.class));
    }

    @Test
    public void testUpdateIncome() throws Exception {
        doNothing().when(incomeDao).update(any(Income.class));
        incomeService.updateIncome(incomes.get(0));
        verify(incomeDao, times(1)).update(any(Income.class));
    }

    @Test
    public void testDeleteIncome() throws Exception {
        doNothing().when(incomeDao).delete(any(Income.class));
        incomeService.deleteIncome(incomes.get(0));
        verify(incomeDao, times(1)).delete(any(Income.class));
    }

    @Test
    public void testFindById() throws Exception {
        when(incomeDao.findyById(anyLong())).thenReturn(mock(Income.class));
        Assert.assertEquals(incomeService.findById(1L).getAmount(), mock(Income.class).getAmount());
        verify(incomeDao, times(1)).findyById(anyLong());
    }

    @Test
    public void testFindAllIncomesInAccount() throws Exception {
        when(incomeDao.getAccountsIncomes(any(Account.class)))
            .thenReturn(incomes);
        Assert.assertEquals(incomeService.findAllIncomesInAccount(account), incomes);
        verify(incomeDao, times(1)).getAccountsIncomes(any(Account.class));
    }

    private List<Income> getIncomeList() {
        Income income1 = new Income(BigDecimal.valueOf(300), LocalDateTime.of(2000, 2, 3, 5, 1), account);
        Income income2 = new Income(BigDecimal.valueOf(500), LocalDateTime.of(2000, 2, 3, 5, 1), account);

        incomes.add(income1);
        incomes.add(income2);
        return incomes;
    }

}