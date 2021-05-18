package com.me.account;

import com.me.account.dto.Account;
import com.me.account.service.AccountService;
import com.me.account.util.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class AccountbalanceApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AccountbalanceApplication.class, args);
	}
}
