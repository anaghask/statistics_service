package com.statistics.controller;

import com.statistics.model.Transaction;
import com.statistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController()
@RequestMapping( "/transactions" )
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController( final TransactionService transactionService ) {
        this.transactionService = transactionService;
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity createTransactions( @Valid @RequestBody Transaction transaction ) {
            transactionService.createTransactions( transaction );
        return ResponseEntity.created( URI.create( "/transactions" ) ).build();
    }

}
