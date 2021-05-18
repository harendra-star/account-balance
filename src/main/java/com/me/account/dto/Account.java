package com.me.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Builder
@Data
public class Account {
    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private LocalDateTime createdAt;
    private Double amount;
    private String transactionType;
    private String relatedTransaction;
}
