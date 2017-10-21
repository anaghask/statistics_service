package com.statistics.service;

import com.statistics.exception.StatisticsNotAvailableException;
import com.statistics.model.Statistics;
import com.statistics.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    private Statistics statistics;

    public void updateStatistics( final Collection<Transaction> dateTransactions ) {
        final DoubleSummaryStatistics summaryStatistics = dateTransactions
                .stream()
                .collect( Collectors.summarizingDouble( Transaction::getAmount ) );
        statistics = Statistics.from( summaryStatistics );
    }

    public Statistics getStatistics() {
        if ( statistics == null ) {
            throw new StatisticsNotAvailableException( "Could not find statistics for the transactions" );
        }
        return statistics;
    }
}
