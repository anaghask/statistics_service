package com.statistics.model;

import com.statistics.utility.TestDataSetupUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StatisticsTest {
    private static final double AMOUNT_TWO = 2.2;
    private static final double MAXIMUM_TRANSACTION_VALUE = 3.3;
    private ConcurrentSkipListMap<Date, Transaction> dateTransactionTreeMap = new ConcurrentSkipListMap<>();

    @Before
    public void setUp() throws Exception {
        final Transaction transactionOne = TestDataSetupUtil.createTransaction().build();
        final Transaction transactionTwo = TestDataSetupUtil
                .createTransaction( AMOUNT_TWO, 1508465544215L )
                .build();
        final Transaction transactionThree = TestDataSetupUtil
                .createTransaction( MAXIMUM_TRANSACTION_VALUE, 1508465544217L )
                .build();
        dateTransactionTreeMap = TestDataSetupUtil
                .createTransactionMap( transactionOne, transactionTwo, transactionThree );
    }

    @Test
    public void shouldCreateStatisticsOutOfDoubleMemoryStatistics() throws Exception {

        final DoubleSummaryStatistics collect = dateTransactionTreeMap
                .values()
                .stream()
                .collect( Collectors.summarizingDouble( Transaction::getAmount ) );

        final Statistics statistics = Statistics.from( collect );
        final Statistics expectedStatistics = TestDataSetupUtil.createStatistics().build();
        assertThat( statistics, is( expectedStatistics ) );

    }
}