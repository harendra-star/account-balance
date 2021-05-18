package com.me.account.service;

import com.me.account.constants.TransactionType;
import com.me.account.dto.Account;
import com.me.account.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AccountService {
    /**
     * 1. First filter all the transactions where  accountId is present in to or from account id
     * 2. Filter out transactions on the criteria of from to to date
     * 3. Take out reversal transactions
     * 4. Find out account balance
     * @param accountList
     * @param accountId
     * @param fromDate
     * @param toDate
     * @return
     */
    public Double getBalance(List<Account> accountList, String accountId, String fromDate, String toDate) {

        if(accountList == null || StringUtils.isEmpty(accountId) || StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate))
            throw  new IllegalArgumentException("Input arguments are not valid");

        Predicate<Account> fromAccountPredicate = account -> account.getFromAccountId().equals(accountId);
        Predicate<Account> toAccountPredicate = account -> account.getFromAccountId().equals(accountId);

        Predicate<Account> fromDatePredicate = account -> account.getCreatedAt().isAfter(DateUtil.parseDate(fromDate));
        Predicate<Account> toDatePredicate = account -> account.getCreatedAt().isBefore(DateUtil.parseDate(toDate));

        List<String> reversalTransactionIds = getReversalTransactionIds(accountList, accountId);
        accountList = accountList.stream()
                .filter(fromAccountPredicate.or(toAccountPredicate))
                .filter(fromDatePredicate.and(toDatePredicate))
                .filter(account -> !reversalTransactionIds.stream().anyMatch(reversalTransaction ->
                        account.getTransactionId().equals(reversalTransaction)))
                .collect(Collectors.toList());

        return accountList.stream()
                .filter(account -> account.getToAccountId().equals(accountId))
                .map(account -> account.getAmount())
                .reduce(0.00, Double::sum) - accountList.stream()
                .filter(account -> account.getFromAccountId().equals(accountId))
                .map(account -> account.getAmount())
                .reduce(0.00, Double::sum);
    }

    private List<String> getReversalTransactionIds(List<Account> accountList, String accountId) {
        Predicate<Account> fromAccountPredicate = account -> account.getFromAccountId().equals(accountId);
        return accountList.stream()
                .filter(fromAccountPredicate)
                .filter(account -> account.getTransactionType().equals(TransactionType.REVERSAL.name()))
                .map(account -> account.getRelatedTransaction())
                .collect(Collectors.toList());
    }
}
