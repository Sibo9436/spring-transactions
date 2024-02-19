package com.sibo.transactions.dto;

import com.sibo.transactions.model.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;

@Data
public class TransactionDto {
    private Integer transactionId;
    @NotNull
    private BigInteger amount;
    @NotNull
    private Currency currency;
    private Instant timestamp;
    @NotNull
    private Integer senderId;
    @NotNull
    private Integer receiverId;
    private String status;
    private String description;

}
