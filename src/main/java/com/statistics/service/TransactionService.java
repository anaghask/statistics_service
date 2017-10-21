package com.statistics.service;

import com.statistics.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class TransactionService {
    private ConcurrentSkipListMap<Date, Transaction> transactions = new ConcurrentSkipListMap<>();
    private final StatisticsService statisticsService;
    private static final long SECONDS_TO_SUBTRACT = 60L;

    @Autowired
    public TransactionService( final StatisticsService statisticsService ) {
        this.statisticsService = statisticsService;
    }

    public void createTransactions( final Transaction transaction ) {
        final Date currentTime = Date.from( Instant.now().minusSeconds( SECONDS_TO_SUBTRACT ) );
        transactions.put( transaction.getTimeStamp(), transaction );

        final ConcurrentSkipListMap<Date, Transaction> dateTransactionTreeMap =
                removeTransactionsBeforeLastMinute( transactions, currentTime );
        statisticsService.updateStatistics( dateTransactionTreeMap.values() );
    }

    private ConcurrentSkipListMap<Date, Transaction> removeTransactionsBeforeLastMinute(
            final ConcurrentSkipListMap<Date, Transaction> transactions, final Date currentTime ) {
        transactions.headMap( currentTime ).clear();
        return transactions;
    }

}
