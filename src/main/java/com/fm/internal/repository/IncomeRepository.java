package com.fm.internal.repository;

import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends PagingAndSortingRepository<Income, Long> {

    List<Income> findAllByAccount(Account account);

    List<Income> findAllByAccountAndDateBetween(Account account, LocalDate start, LocalDate finish);

}
