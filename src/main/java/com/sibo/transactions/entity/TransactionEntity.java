package com.sibo.transactions.entity;

import com.sibo.transactions.model.Currency;
import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;

@Data
public class TransactionEntity {
    private Integer transactionId;
    private BigInteger amount;
    private Currency currency;
    private Instant timestamp;
    private Integer senderId;
    private Integer receiverId;
    private String status;
    private String description;
}
