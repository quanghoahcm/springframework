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
		Account account1 = context.getBean("myAccount",Account.class);		
		/*System.out.println(account.createAccount());
		System.out.println(account.cardDetail());*/
		
		Account account2 = context.getBean("myAccount",Account.class);
		boolean isSame = (account1 == account2);
		System.out.println("Account1 and Account 2 to same object: " + isSame);
		System.out.println("Account1 Hash : " + account1.hashCode());
		System.out.println("Account2 Hash : " + account2.hashCode());
	}
}
