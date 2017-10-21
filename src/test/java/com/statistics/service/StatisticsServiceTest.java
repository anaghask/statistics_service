package com.statistics.service;

import com.statistics.exception.StatisticsNotAvailableException;
import com.statistics.model.Statistics;
import com.statistics.model.Transaction;
import com.statistics.utility.TestDataSetupUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StatisticsServiceTest {
    private static final double AMOUNT_TWO = 2.2;
    private static final double MAXIMUM_TRANSACTION_VALUE = 3.3;
    private StatisticsService statisticsService;
    private List<Transaction> transactions;

    @Before
    public void setUp() throws Exception {
        final Transaction transactionOne = TestDataSetupUtil.createTransaction().build();
        final Transaction transactionTwo = TestDataSetupUtil
                .createTransaction( AMOUNT_TWO, 1508465544215L )
                .build();
        final Transaction transactionThree = TestDataSetupUtil
                .createTransaction( MAXIMUM_TRANSACTION_VALUE, 1508465544217L )
                .build();
        transactions = asList( transactionOne, transactionTwo, transactionThree );
        statisticsService = new StatisticsService();
    }

    @Test
    public void shouldGiveRightStatistics() throws Exception {
        statisticsService.updateStatistics( transactions );

        final Statistics expectedStatistics = TestDataSetupUtil.createStatistics().build();

        assertThat( statisticsService.getStatistics(), is( expectedStatistics ) );
    }

    @Test( expected = StatisticsNotAvailableException.class )
    public void shouldThrowStatisticsNotAvailableExceptionIfTheTransactionsAreNotInitiated() throws Exception {
        statisticsService.getStatistics();
    }

}