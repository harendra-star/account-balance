package com.me.account.util;

import com.me.account.constants.AccountHeaders;
import com.me.account.dto.Account;
import com.me.account.service.AccountService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CSVUtil {

    private static String TYPE = "text/csv";

    @Autowired
    AccountService accountService;

    public List<Account> readFile(InputStream is) throws IOException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withDelimiter(',')
                .withHeader(AccountHeaders.class)
                .withFirstRecordAsHeader()
                .parse(fileReader);
        return StreamSupport.stream(records.spliterator(), false).
                map(record -> Account.builder().transactionId(record.get(AccountHeaders.transactionId))
                        .fromAccountId(record.get(AccountHeaders.fromAccountId))
                        .toAccountId(record.get(AccountHeaders.toAccountId))
                        .createdAt(DateUtil.parseDate(record.get(AccountHeaders.createdAt)))
                        .amount(Double.parseDouble(record.get(AccountHeaders.amount)))
                        .transactionType(record.get(AccountHeaders.transactionType))
                        .relatedTransaction(record.get(AccountHeaders.relatedTransaction))
                        .build()).collect(Collectors.toList());
    }

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
}
