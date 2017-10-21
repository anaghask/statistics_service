package com.statistics.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistics.model.Transaction;

import java.time.Instant;
import java.util.Random;

public class TransactionRequestGenerator {
    public static void main( String[] args ) throws JsonProcessingException {
        Integer transactionCount = 10;
        final ObjectMapper objectMapper = new ObjectMapper();
        if ( args.length > 0 ) {
            transactionCount = Integer.parseInt( args[0] );
        }

        for ( int i = 0; i < transactionCount; i++ ) {
            final long epochMilli = Instant.now().plusSeconds( 60L ).toEpochMilli();
            final Random random = new Random( 100L );
            final Transaction transaction = TestDataSetupUtil.createTransaction( random.nextDouble(), epochMilli ).build();
            final String transactionString = objectMapper.writeValueAsString( transaction );
            System.out.println( transactionString );
        }
    }
}
