package com.fm.internal.dtos;

import com.fm.internal.models.OutcomeType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class OutcomeDto {
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?$")
    private String amount;
    private String defaultAmount;
    @Pattern(regexp = "^((#(([^#\\s]+)\\s+)*(#([^#\\s]+)))|(#([^#\\s]+))?)$")
    private String hashTags;
    @NotEmpty
    private String date;
    private String note;
    private long accountId;
    @NotNull
    @Min(value = 1)
    private long outcomeTypeId;
    private List<OutcomeType> outcomeTypes;
    private OutcomeType outcomeType;

    public OutcomeDto() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(String defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getOutcomeTypeId() {
        return outcomeTypeId;
    }

    public void setOutcomeTypeId(long outcomeTypeId) {
        this.outcomeTypeId = outcomeTypeId;
    }

    public List<OutcomeType> getOutcomeTypes() {
        return outcomeTypes;
    }

    public void setOutcomeTypes(List<OutcomeType> outcomeTypes) {
        this.outcomeTypes = outcomeTypes;
    }

    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    public void setOutcomeType(OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }

    public String getHashTags() {
        return hashTags;
    }

    public void setHashTags(String hashTags) {
        this.hashTags = hashTags;
    }
}
