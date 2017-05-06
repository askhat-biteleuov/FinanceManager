package com.fm.internal.services.implementation;

import com.fm.internal.dtos.RangeDto;

import java.time.LocalDate;

public class RangeServiceImpl implements RangeService {

    @Override
    public boolean checkRange(RangeDto rangeDto) {
        return !(rangeDto.getStart() == null || rangeDto.getEnd() == null ||
                rangeDto.getStart().isEmpty() || rangeDto.getEnd().isEmpty());
    }

    @Override
    public LocalDate setupStart(RangeDto rangeDto) {
        if (checkRange(rangeDto)) {
            return LocalDate.parse(rangeDto.getStart());
        }
        LocalDate now = LocalDate.now();
        return LocalDate.of(now.getYear(), now.getMonth(), 1);
    }

    @Override
    public LocalDate setupEnd(RangeDto rangeDto) {
        if (checkRange(rangeDto)) {
            return LocalDate.parse(rangeDto.getEnd());
        }
        return LocalDate.now();
    }

}
