package com.sibo.transactions;

import com.sibo.transactions.dto.TransactionDto;
import com.sibo.transactions.entity.TransactionEntity;
import com.sibo.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class TransactionsController {
    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public List<TransactionDto> getTransactions() throws Exception{
        List<TransactionDto> transactions = transactionService.getTransactions();
        return transactions;
    }
}
