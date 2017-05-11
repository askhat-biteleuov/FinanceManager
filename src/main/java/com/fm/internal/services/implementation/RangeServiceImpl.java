package com.fm.internal.services.implementation;

import com.fm.internal.dtos.RangeDto;
import com.fm.internal.services.RangeService;
import org.springframework.security.access.method.P;

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
        LocalDate start = LocalDate.of(now.getYear(), now.getMonth(), 1);
        if (now.getMonth().getValue() < 10) {
            rangeDto.setStart(start.getYear() + "-" + "0" + start.getMonth().getValue() + "-" + "0" + 1);
        } else {
            rangeDto.setStart(start.getYear() + "-" + start.getMonth().getValue() + "-" + "0" + 1);
        }
        return start;
    }

    @Override
    public LocalDate setupEnd(RangeDto rangeDto) {
        if (checkRange(rangeDto)) {
            return LocalDate.parse(rangeDto.getEnd());
        }
        LocalDate end = LocalDate.now();
        if (end.getMonth().getValue() < 10) {
            rangeDto.setEnd(end.getYear() + "-" + "0" + end.getMonth().getValue() + "-" + end.getDayOfMonth());
        } else {
            rangeDto.setEnd(end.getYear() + "-" + end.getMonth().getValue() + "-" + end.getDayOfMonth());
        }
        return end;
    }

}
