package com.me.account.controller;

import com.me.account.dto.ResponseMessage;
import com.me.account.service.FileReaderService;
import com.me.account.util.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/csv")
public class CSVController {

    @Autowired
    FileReaderService fileReaderService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> calculateBalance(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("accountId")  String accountId,
                                                      @RequestParam("fromDate") String fromDate,
                                                      @RequestParam("toDate") String toDate) {
        String message = "";
        if (CSVUtil.hasCSVFormat(file)) {
            try {
                Double accountBalance = fileReaderService.parseFile(file.getInputStream(), accountId, fromDate, toDate);
                message = "Account Balance is :" + accountBalance;
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not process the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}
