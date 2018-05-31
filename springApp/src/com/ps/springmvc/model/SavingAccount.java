package com.ps.springmvc.model;

import com.ps.springmvc.Account;
import com.ps.springmvc.Card;

public class SavingAccount implements Account  {

	private Card atmCard;
	public SavingAccount(Card card) {
		atmCard = card;
	}
	@Override
	public String createAccount() {
		// TODO Auto-generated method stub
		return "Created Saving Account Sucessfully";
	}

	@Override
	public String cardDetail() {
		// TODO Auto-generated method stub
		return atmCard.cardDetail();
	}
	
}
