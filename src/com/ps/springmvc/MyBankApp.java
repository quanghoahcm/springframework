package com.ps.springmvc;

import com.ps.springmvc.model.CurrentAccount;
import com.ps.springmvc.model.SavingAccount;

public class MyBankApp {

	public static void main(String []args) {
		SavingAccount svaccount = new SavingAccount();
		CurrentAccount craccount = new CurrentAccount();
		System.out.println(svaccount.createAccount());
		System.out.println(craccount.createAccount());
	}
}
