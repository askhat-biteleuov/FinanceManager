package com.fm.internal.repository;

import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OutcomeTypeRepository extends PagingAndSortingRepository<OutcomeType, Long> {

    OutcomeType findByUserAndName(User user, String name);

}
