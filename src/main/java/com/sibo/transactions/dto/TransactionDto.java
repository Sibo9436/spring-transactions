package com.sibo.transactions.dto;

import com.sibo.transactions.model.Currency;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private String transactionID;
    private BigInteger amount;
    private Currency currency;
    private LocalDateTime timestamp;
    private String senderID;
    private String receiverID;
    private String status;
}
