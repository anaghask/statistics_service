package com.statistics.service;

import com.statistics.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith( MockitoJUnitRunner.class )
public class TransactionServiceTest {
    @Mock private
    StatisticsService statisticsService;

    private TransactionService transactionService;
    private Transaction.TransactionBuilder transactionBuilder;

    @Before
    public void setUp() throws Exception {
        transactionService = new TransactionService( statisticsService );
        transactionBuilder = Transaction.builder()
                .amount( 1.1 )
                .timeStamp( Date.from( Instant.ofEpochMilli( 1508465544214L ) ) );
    }

    @Test
    public void shouldNotThrowExceptionForTransactionLess1MinuteOld() throws Exception {
        final Transaction currentTimestamp = transactionBuilder.timeStamp( Date.from( Instant.now() ) ).build();
        transactionService.createTransactions( currentTimestamp );
        verify( statisticsService ).updateStatistics( any() );
    }
}