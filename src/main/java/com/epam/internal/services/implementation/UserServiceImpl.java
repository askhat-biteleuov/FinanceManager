package com.epam.internal.services.implementation;

import com.epam.internal.daos.UserDao;
import com.epam.internal.dtos.RegistrationDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.UserService;
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
        dao.create(user);
        Account initAccount = new Account("Кошелек", BigDecimal.ZERO, null, user);
        accountService.createAccount(initAccount);
        OutcomeType[] initOutcomeTypes = {
                new OutcomeType("Еда", new BigDecimal(5000), user),
                new OutcomeType("Транспорт", new BigDecimal(1000), user),
                new OutcomeType("Интернет", new BigDecimal(1000), user),
                new OutcomeType("Мобильная связь", new BigDecimal(500), user),
                new OutcomeType("Развлечения", new BigDecimal(3000), user),
                new OutcomeType("Покупки", new BigDecimal(4000), user),
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
        UserInfo userInfo = new UserInfo(registrationDto.getFirstName(), registrationDto.getLastName());
        User user = new User(registrationDto.getEmail(), encoder.encode(registrationDto.getPassword()), userInfo);
        dao.create(user);
    }
}
