package com.ps.springmvc.model;

import com.ps.springmvc.Card;

public class ATMCard implements Card {

	@Override
	public String cardDetail() {
		// TODO Auto-generated method stub
		return "ATM has been issued successfully";
	}

}
