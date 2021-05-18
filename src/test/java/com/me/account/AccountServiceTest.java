package com.me.account;

import com.me.account.constants.TransactionType;
import com.me.account.dto.Account;
import com.me.account.service.AccountService;
import com.me.account.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AccountServiceTest {



    @Autowired
    AccountService accountService;


    @BeforeEach
    public void init() {

    }
    @Test
    public void getBalance(){
        Double balance = accountService.getBalance(accountDataSet1(), "ACC334455", "20/10/2018 12:00:00", "20/10/2018 19:00:00");
        Assertions.assertEquals(-25.0, balance);
    }

    @Test
    public void getBalanceForNonExistingAccount(){
        Double balance = accountService.getBalance(accountDataSet1(), "ACL334455", "20/10/2018 12:00:00", "20/10/2018 19:00:00");
        Assertions.assertEquals(0.0, balance);
    }

    @Test
    public void getBalanceException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.getBalance(accountDataSet1(), "", "20/10/2018 12:00:00", "20/10/2018 19:00:00"));
    }

    @Test
    public void getBalanceWithoutReversal(){
        Double balance = accountService.getBalance(accountDataSet2(), "ACC334455", "20/10/2018 12:00:00", "20/10/2018 19:00:00");
        Assertions.assertEquals(-35.0, balance);
    }

    private List<Account> accountDataSet1(){
        List<Account> accountList = new ArrayList<>();
        accountList.add(Account.builder()
                .transactionId("TX10001")
                .fromAccountId("ACC334455")
                .toAccountId("ACC778899")
                .createdAt(DateUtil.parseDate("20/10/2018 17:33:43"))
                .amount(25.00)
                .transactionType(TransactionType.PAYMENT.name())
                .build());
        accountList.add(Account.builder()
                .transactionId("TX10002")
                .fromAccountId("ACC334455")
                .toAccountId("ACC998877")
                .createdAt(DateUtil.parseDate("20/10/2018 17:33:43"))
                .amount(10.50)
                .transactionType(TransactionType.PAYMENT.name())
                .build());
        accountList.add(Account.builder()
                .transactionId("TX10003")
                .fromAccountId("ACC998877")
                .toAccountId("ACC778899")
                .createdAt(DateUtil.parseDate("20/10/2018 18:00:00"))
                .amount(5.00)
                .transactionType(TransactionType.PAYMENT.name())
                .build());
        accountList.add(Account.builder()
                .transactionId("TX10004")
                .fromAccountId("ACC334455")
                .toAccountId("ACC998877")
                .createdAt(DateUtil.parseDate("20/10/2018 19:45:00"))
                .amount(10.50)
                .transactionType(TransactionType.REVERSAL.name())
                .relatedTransaction("TX10002")
                .build());
        accountList.add(Account.builder()
                .transactionId("TX10005")
                .fromAccountId("ACC334455")
                .toAccountId("ACC778899")
                .createdAt(DateUtil.parseDate("21/10/2018 09:30:00"))
                .amount(7.25)
                .transactionType(TransactionType.PAYMENT.name())
                .build());
        return accountList;
    }

    private List<Account> accountDataSet2(){
        List<Account> accountList = new ArrayList<>();
        accountList.add(Account.builder()
                .transactionId("TX10001")
                .fromAccountId("ACC334455")
                .toAccountId("ACC778899")
                .createdAt(DateUtil.parseDate("20/10/2018 17:33:43"))
                .amount(25.00)
                .transactionType(TransactionType.PAYMENT.name())
                .build());
        accountList.add(Account.builder()
                .transactionId("TX10002")
                .fromAccountId("ACC334455")
                .toAccountId("ACC998877")
                .createdAt(DateUtil.parseDate("20/10/2018 17:33:43"))
                .amount(10.50)
                .transactionType(TransactionType.PAYMENT.name())
                .build());
        accountList.add(Account.builder()
                .transactionId("TX10003")
                .fromAccountId("ACC998877")
                .toAccountId("ACC778899")
                .createdAt(DateUtil.parseDate("20/10/2018 18:00:00"))
                .amount(5.00)
                .transactionType(TransactionType.PAYMENT.name())
                .build());
        accountList.add(Account.builder()
                .transactionId("TX10005")
                .fromAccountId("ACC334455")
                .toAccountId("ACC778899")
                .createdAt(DateUtil.parseDate("21/10/2018 09:30:00"))
                .amount(7.25)
                .transactionType(TransactionType.PAYMENT.name())
                .build());
        return accountList;
    }

}
