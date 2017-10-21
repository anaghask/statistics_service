package com.statistics.utility;

import com.statistics.model.Statistics;
import com.statistics.model.Transaction;

import java.time.Instant;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class TestDataSetupUtil {
    private static final double MINIMUM_TRANSACTION_VALUE = 1.1;
    private static final double MAXIMUM_TRANSACTION_VALUE = 3.3;
    private static final double AVERAGE_TRANSACTION_VALUE = 2.1999999999999997;
    private static final double SUM_OF_TRANSACTION_AMOUNT = 6.6;
    private static final int TRANSACTION_COUNT = 3;

    public static Statistics.StatisticsBuilder createStatistics() {
        return Statistics
                .builder()
                .averageTransactionValue( AVERAGE_TRANSACTION_VALUE )
                .maximumTransactionValue( MAXIMUM_TRANSACTION_VALUE )
                .minimumTransactionValue( MINIMUM_TRANSACTION_VALUE )
                .sumOfTransactionAmount( SUM_OF_TRANSACTION_AMOUNT )
                .transactionCount( TRANSACTION_COUNT );
    }

    public static Transaction.TransactionBuilder createTransaction() {
        return createTransaction( MINIMUM_TRANSACTION_VALUE, 1508465544214L );
    }
    public static Transaction.TransactionBuilder createTransaction( final long epochMilli ) {
        return createTransaction( MINIMUM_TRANSACTION_VALUE, epochMilli );
    }

    public static Transaction.TransactionBuilder createTransaction( final double minimumTransactionValue,
            final long epochMilli ) {
        return Transaction.builder()
                .amount( minimumTransactionValue )
                .timeStamp( Date.from( Instant.ofEpochMilli( epochMilli ) ) );
    }

    public static ConcurrentSkipListMap<Date, Transaction> createTransactionMap( final Transaction... transactions ) {
        ConcurrentSkipListMap<Date, Transaction> dateTransactionTreeMap = new ConcurrentSkipListMap<>();
        for ( Transaction transaction: transactions) {
            dateTransactionTreeMap.put( transaction.getTimeStamp(), transaction );
        }
        return dateTransactionTreeMap;
    }

}
