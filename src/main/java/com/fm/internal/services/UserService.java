package com.fm.internal.services;

import com.fm.internal.dtos.RegistrationDto;
import com.fm.internal.models.User;
import com.fm.internal.models.UserInfo;

public interface UserService {

    User findByEmail(String email);

    User getLoggedUser();

    void createUser(User user);

    void deleteUserByEmail(String email);

    void deleteUser(User user);

    void updateUser(User user);

    void updateUserInfo(User user, UserInfo info);

    void createUser(RegistrationDto registrationDto);

}
