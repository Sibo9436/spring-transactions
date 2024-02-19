package com.sibo.transactions.controller;

import com.sibo.transactions.dto.TransactionDto;
import com.sibo.transactions.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/transactions", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})

public class TransactionsController {
    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping
    public ResponseEntity<Void> addTransaction(@RequestBody @Validated TransactionDto body) throws Exception {
        transactionService.addTransaction(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Void> updateTransaction(@RequestBody @Validated TransactionDto body, @PathVariable int transactionId) throws Exception {
        transactionService.updateTransaction(body, transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int transactionId) throws Exception {
        transactionService.deleteTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable int transactionId) throws Exception {
        Optional<TransactionDto> dtoOpt = transactionService.getTransactionById(transactionId);
        return ResponseEntity.of(dtoOpt);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getFilteredTransactions( TransactionDto params) {
        List<TransactionDto> transactions = transactionService.filter(params);
        return ResponseEntity.ok(transactions);
    }
}
