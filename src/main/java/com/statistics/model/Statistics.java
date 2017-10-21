package com.statistics.model;

import lombok.Builder;
import lombok.Value;

import java.util.DoubleSummaryStatistics;

@Value
@Builder( toBuilder = true )
public class Statistics {
    private double sumOfTransactionAmount;
    private double averageTransactionValue;
    private double maximumTransactionValue;
    private double minimumTransactionValue;
    private double transactionCount;

    public static Statistics from( final DoubleSummaryStatistics summaryStatistics ) {
        return Statistics.builder()
                .averageTransactionValue( summaryStatistics.getAverage() )
                .maximumTransactionValue( summaryStatistics.getMax() )
                .minimumTransactionValue( summaryStatistics.getMin() )
                .sumOfTransactionAmount( summaryStatistics.getSum() )
                .transactionCount( summaryStatistics.getCount() )
                .build();

    }

}
