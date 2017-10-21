package com.statistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistics.controller.errorhandling.ControllerErrorHandler;
import com.statistics.model.Transaction;
import com.statistics.service.TransactionService;
import com.statistics.utility.TestDataSetupUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {
    private String serialisedTransaction;
    private Transaction createdTransaction;
    @Mock
    private TransactionService transactionService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        initMocks( this );
        final TransactionController transactionController = new TransactionController( transactionService );

        this.mvc = MockMvcBuilders
                .standaloneSetup( transactionController )
                .setControllerAdvice( new ControllerErrorHandler() )
                .build();
        createdTransaction = TestDataSetupUtil.createTransaction( Instant.now().toEpochMilli() ).build();
        serialisedTransaction = objectMapper.writeValueAsString( createdTransaction );
    }

    @Test
    public void shouldPostTransaction() throws Exception {
        mvc.perform( MockMvcRequestBuilders.post( "/transactions" )
                .content( serialisedTransaction )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isCreated() );
        verify( transactionService ).createTransactions( createdTransaction );
    }

    @Test
    public void shouldReturn204IfTransactionServiceGivesIndicationThatTimestampIsOlder() throws Exception {
        createdTransaction = TestDataSetupUtil.createTransaction().build();
        serialisedTransaction = objectMapper.writeValueAsString( createdTransaction );
        TestDataSetupUtil.createTransaction().build();
        mvc.perform( MockMvcRequestBuilders.post( "/transactions" )
                .content( serialisedTransaction )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNoContent() );
    }

    @Test
    public void shouldReturn500IfTransactionServiceThrowsException() throws Exception {
        doThrow( new RuntimeException( "Timestamp is older than 1 minute" ) )
                .when( transactionService ).createTransactions( createdTransaction );

        mvc.perform( MockMvcRequestBuilders.post( "/transactions" )
                .content( serialisedTransaction )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isInternalServerError() );
        verify( transactionService ).createTransactions( createdTransaction );
    }

}