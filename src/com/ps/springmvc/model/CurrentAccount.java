package com.ps.springmvc.model;

import com.ps.springmvc.Account;
import com.ps.springmvc.Card;

public class CurrentAccount implements Account {

	private Card creditCard;
	public void setCreditCard(Card creditCard) {
		this.creditCard = creditCard;
	}

	@Override
	public String createAccount() {
		// TODO Auto-generated method stub
		return "Created current account sucessfully";
	}

	@Override
	public String cardDetail() {
		// TODO Auto-generated method stub
		return this.creditCard.cardDetail();
	}

	
}
