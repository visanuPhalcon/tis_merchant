package com.promptnow.utility;

public class UtilCardFilter {
	public enum CardType {
		UNINDENTIFIED,
		CREDIT_CARD,
		VIRTUAL_CREDIT_CARD,
		LOAN_CARD,
		MULTIPURPOSE_LOAN_CARD,
		GOVERNMENT_SERVICE_CARD,
		CORPERATE_CARD,
		CYCLE_LOAN_CARD
	}
	
	public CardType stringToCardTypeFromService(String cardType, String isVCard) {
		if(isVCard.equalsIgnoreCase("Y")) {
			return CardType.VIRTUAL_CREDIT_CARD;
		} else {
			if(cardType.equalsIgnoreCase("CC")) {
				return CardType.CREDIT_CARD;
			} else if(cardType.equalsIgnoreCase("RL")) {
				return CardType.LOAN_CARD;
			} else if(cardType.equalsIgnoreCase("CL")) {
				return CardType.CYCLE_LOAN_CARD;
			} else if(cardType.equalsIgnoreCase("PL")) {
				return CardType.MULTIPURPOSE_LOAN_CARD;
			} else if(cardType.equalsIgnoreCase("GSC")) {
				return CardType.GOVERNMENT_SERVICE_CARD;
			} else if(cardType.equalsIgnoreCase("CORP")) {
				return CardType.CORPERATE_CARD;
			}
		}
		return CardType.UNINDENTIFIED;
	}
	
	public String getCardDataPayMethodFromProductID(CardType cardType, String productID) {
		String result = "";
		char firstCharInProductID = productID.charAt(0);
		if(firstCharInProductID == '3') {
			return "JCB";
		} else if(firstCharInProductID == '4') {
			return "VISA";
		} else if(firstCharInProductID == '5') {
			return "Master";
		}
		return result;
	}
}
