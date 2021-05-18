package com.me.account.service;

import com.me.account.dto.Account;
import com.me.account.exception.FileReaderApiException;
import com.me.account.util.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class FileReaderService {
    @Autowired
    CSVUtil csvUtil;
    @Autowired
    AccountService accountService;

    public Double parseFile(InputStream inputStream, String accountId, String fromDate, String toDate) {
        try {
            List<Account> accountList = csvUtil.readFile(inputStream);
            return accountService.getBalance(accountList, accountId, fromDate, toDate);
        } catch (Exception ex) {
            throw new FileReaderApiException(ex.getMessage(), ex);
        }
    }
}
