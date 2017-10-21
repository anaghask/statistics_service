package com.statistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statistics.controller.errorhandling.ControllerErrorHandler;
import com.statistics.exception.StatisticsNotAvailableException;
import com.statistics.model.Statistics;
import com.statistics.service.StatisticsService;
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

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTest {
    @Mock
    private StatisticsService statisticsService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        initMocks( this );
        final StatisticsController statisticsController = new StatisticsController( statisticsService );
        this.mvc = MockMvcBuilders
                .standaloneSetup( statisticsController )
                .setControllerAdvice( new ControllerErrorHandler() )
                .build();
    }

    @Test
    public void shouldReturnValidStatistics() throws Exception {
        final Statistics expectedStatistics = TestDataSetupUtil.createStatistics().build();
        when( statisticsService.getStatistics() ).thenReturn( expectedStatistics );
        final String deserializedStatistics = objectMapper.writeValueAsString( expectedStatistics );
        mvc.perform( MockMvcRequestBuilders.get( "/statistics" )
                .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().json( deserializedStatistics ) );
    }

    @Test
    public void shouldReturnNoContentWhenStatisticsNotAvailableExceptionIsThrownByService() throws Exception {
        when( statisticsService.getStatistics() ).thenThrow(
                new StatisticsNotAvailableException( "Could not find statistics for the transactions" ) );

        mvc.perform( MockMvcRequestBuilders.get( "/statistics" )
                .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNoContent() );
    }

    @Test
    public void shouldReturnInternalServerErrorWhenRuntimeExceptionIsThrownByService() throws Exception {
        when( statisticsService.getStatistics() ).thenThrow( new RuntimeException( "Internal error" ) );

        mvc.perform( MockMvcRequestBuilders.get( "/statistics" )
                .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isInternalServerError() );
    }
}