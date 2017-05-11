package com.fm.internal.services.implementation;

import com.fm.internal.daos.UserDao;
import com.fm.internal.dtos.RegistrationDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;
import com.fm.internal.models.UserInfo;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.CurrencyService;
import com.fm.internal.services.OutcomeTypeService;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private PasswordEncoder encoder;


    @Override
    public User findByEmail(String email) {
        return dao.getUserByEmail(email);
    }

    @Override
    public User getLoggedUser() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            return null;
        }
        return dao.getUserByEmail(email);
    }

    @Override
    public void createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        dao.add(user);
        Account initWalletAccount = new Account("Кошелек", BigDecimal.ZERO, null, user, currencyService.findCurrencyByCharCode("RUB"));
        Account initSalaryAccount = new Account("Зарплатный", BigDecimal.ZERO, null, user, currencyService.findCurrencyByCharCode("RUB"));
        accountService.createAccount(initWalletAccount);
        accountService.createAccount(initSalaryAccount);
        OutcomeType[] initOutcomeTypes = {
                new OutcomeType("Еда", new BigDecimal(5000), user),
                new OutcomeType("Транспорт", new BigDecimal(1000), user),
                new OutcomeType("Интернет", new BigDecimal(1000), user),
                new OutcomeType("Мобильная связь", new BigDecimal(500), user),
                new OutcomeType("Развлечения", new BigDecimal(3000), user),
                new OutcomeType("Покупки", new BigDecimal(4000), user),
                new OutcomeType("Переводы", BigDecimal.ONE, user)
        };
        Arrays.stream(initOutcomeTypes).forEach((outcomeType -> outcomeTypeService.addOutcomeType(outcomeType)));
    }

    @Override
    public void deleteUserByEmail(String email) {
        dao.delete(dao.getUserByEmail(email));
    }

    @Override
    public void deleteUser(User user) {
        dao.delete(user);
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public void updateUserInfo(User user, UserInfo info) {
        user.setInfo(info);
        dao.update(user);
    }

    @Override
    public void createUser(RegistrationDto registrationDto) {
        UserInfo userInfo = new UserInfo(registrationDto.getFirstName(), registrationDto.getLastName(),
                currencyService.findCurrencyByCharCode(registrationDto.getCurrency()));
        User user = new User(registrationDto.getEmail(), registrationDto.getPassword(), userInfo);
        createUser(user);
    }
}
