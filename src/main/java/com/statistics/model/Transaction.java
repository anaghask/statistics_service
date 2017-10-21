package com.statistics.model;

import com.statistics.validation.ValidTimeStamp;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder( toBuilder = true )
public class Transaction {
    private final Double amount;
    @ValidTimeStamp(message = "Given timestamp is older than 60 seconds")
    private final Date timeStamp;
}
