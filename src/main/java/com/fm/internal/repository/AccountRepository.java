package com.fm.internal.repository;

import com.fm.internal.models.Account;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface AccountRepository extends CrudRepository<Account, Long> {

    BigDecimal getSumOfAllBalancesOfAccounts();

}
