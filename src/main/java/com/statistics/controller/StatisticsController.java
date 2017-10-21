package com.statistics.controller;

import com.statistics.model.Statistics;
import com.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping( "/statistics" )
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController( final StatisticsService statisticsService ) {
        this.statisticsService = statisticsService;
    }

    @RequestMapping( method = RequestMethod.GET )
    @ResponseBody
    public Statistics createTransactions() {
        return statisticsService.getStatistics();
    }
}
