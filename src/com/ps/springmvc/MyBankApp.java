package com.ps.springmvc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ps.springmvc.model.CurrentAccount;
import com.ps.springmvc.model.SavingAccount;

public class MyBankApp {

	private static ClassPathXmlApplicationContext context;

	public static void main(String []args) {
	/*	SavingAccount svaccount = new SavingAccount();
		CurrentAccount craccount = new CurrentAccount();
		System.out.println(svaccount.createAccount());*/
		
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Account account = context.getBean("myAccount",Account.class);		
		System.out.println(account.createAccount());
		System.out.println(account.cardDetail());
	}
}
