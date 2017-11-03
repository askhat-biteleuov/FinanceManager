package com.fm.internal.services.implementation;

import com.fm.internal.dtos.RangeDto;
import com.fm.internal.services.RangeService;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
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
        rangeDto.setStart(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return start;
    }

    @Override
    public LocalDate setupEnd(RangeDto rangeDto) {
        if (checkRange(rangeDto)) {
            return LocalDate.parse(rangeDto.getEnd());
        }
        LocalDate end = LocalDate.now();
        rangeDto.setEnd(end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return end;
    }

}
