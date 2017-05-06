package com.fm.internal.services.implementation;

import com.fm.internal.dtos.RangeDto;

import java.time.LocalDate;

public interface RangeService {

    boolean checkRange(RangeDto rangeDto);
    LocalDate setupStart(RangeDto rangeDto);
    LocalDate setupEnd(RangeDto rangeDto);

}
