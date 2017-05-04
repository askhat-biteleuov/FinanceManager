package com.fm.internal.services;

import com.fm.internal.dtos.StatusBarDto;
import com.fm.internal.models.User;

public interface StatusBarService {
    StatusBarDto getStatusBar(User user);
}
