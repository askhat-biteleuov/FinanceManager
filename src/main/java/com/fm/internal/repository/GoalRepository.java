package com.fm.internal.repository;

import com.fm.internal.models.Goal;
import com.fm.internal.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GoalRepository extends PagingAndSortingRepository<Goal, Long> {

    List<Goal> findByUser(User user);

    List<Goal> findWithNoIncomesByMonth(User user);

}
