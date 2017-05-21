package com.fm.internal.dtos;

import com.fm.internal.models.Outcome;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class OutcomeTypeDto {
    private long id;

    @NotEmpty
    @Size(max = 15)
    private String name;
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?$")
    private String limit;

    private List<Outcome> outcomes;

    public OutcomeTypeDto() {
    }

    public OutcomeTypeDto(String name, String limit) {
        this.name = name;
        this.limit = limit;
    }

    public OutcomeTypeDto(long id, String name, String limit, List<Outcome> outcomes) {
        this.id = id;
        this.name = name;
        this.limit = limit;
        this.outcomes = outcomes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }
}
