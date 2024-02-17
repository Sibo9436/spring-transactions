package com.sibo.transactions.entity;

import com.sibo.transactions.model.Currency;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class TransactionEntity {
    private Long transactionID;
    private BigInteger amount;
    private Currency currency;
    private LocalDateTime timestamp;
    private Long senderId;
    private Long receiverId;
    private String status;
}
