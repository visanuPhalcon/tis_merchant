package com.promptnow.utility;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UtilFormat
{
	private static final DecimalFormat moneyFormat = new DecimalFormat("#,###,##0.00");
	private static final DecimalFormat moneyServiceFormat = new DecimalFormat("######0.00");
	private static final DecimalFormat feeFormat = new DecimalFormat("#,###,##0.0000");
	private static final DecimalFormat feeTransferFormat = new DecimalFormat("#,###,##0.00");
	private static final DecimalFormat pointFormat = new DecimalFormat("#,###,##0");
	private static final DecimalFormat curAmountFormat = new DecimalFormat("#,###,##0.#########");

	private static final int MASK_COUNT = 4;
	private static final char MASK_CHAR = 'X';
	
	//*** Update by Vasin
	public static String formatMoney(String value)
	{
		String money = formatBalance(value);
		

		return getSign(value) + money;
	}
	public static String getSign(String value) {
		if (value.indexOf('+') != -1) {
			return "+";
		} else if (value.indexOf('-') != -1) {
			return "-";
		}
		return "";
	}
	//********

	public static String formatBalance(String balance)
	{
		try
		{
			return moneyFormat.format(Double.valueOf(balance));
		} catch (Exception e)
		{
			return balance;
		}
	}

	public static String formatBalanceServices(String balance)
	{
		try
		{
			return moneyServiceFormat.format(Double.valueOf(balance));
		} catch (Exception e)
		{
			return balance;
		}
	}

	public static String formatFee(String fee)
	{
		if (fee.contains(";"))
		{
			fee = fee.substring(0, fee.indexOf(";"));
		}
		try
		{
			return feeFormat.format(Double.valueOf(fee));
		} catch (Exception e)
		{
			return fee;
		}
	}

	public static String formatTransferFee(String fee)
	{
		if (fee.contains(";"))
		{
			fee = fee.substring(0, fee.indexOf(";"));
		}
		try
		{
			return feeTransferFormat.format(Double.valueOf(fee));
		} catch (Exception e)
		{
			return fee;
		}
	}

	public static String formatPoint(String point)
	{
		try
		{
			return pointFormat.format(Double.valueOf(point));
		} catch (Exception e)
		{
			return point;
		}
	}

	public static String curBalance(String balance)
	{
		try
		{
			return curAmountFormat.format(Double.valueOf(balance));
		} catch (Exception e)
		{
			return balance;
		}
	}

	public static String maskAcc(String acc)
	{
		try
		{
			StringBuilder result = new StringBuilder();
			if (acc.length() >= 4)
			{
				for (int i = 0; i < acc.length(); i++)
				{
					if (i < MASK_COUNT)
					{
						result.append(MASK_CHAR);
					} else
					{
						result.append(acc.charAt(i));
					}
				}
			}
			return result.toString();
		} catch (Exception e)
		{
			
			return acc;
		}
	}

	public static String maskAccCredit(String acc)
	{
		try
		{
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < acc.length(); i++)
			{
				if (i == 6 || i == 7 || i == 8 || i == 9 || i == 10 || i == 11)
				{
					result.append(MASK_CHAR);
				} else
				{
					result.append(acc.charAt(i));
				}
			}
			return result.toString();
		} catch (Exception e)
		{
			
			return acc;
		}
	}
	
	public static boolean isEmailFormat(String mail)
	{
		/*
		boolean result = true;
		
		if (mail == null || mail.length() < 6 || mail.equals("") || mail.indexOf("@") < 1
				|| (mail.indexOf(".", mail.indexOf("@")) < 0) || (mail.charAt(mail.length()-1) == '.'))
			result = false;
		*/
		
		boolean result = false;
		try
		{
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(mail);
            result = matcher.matches();
        }
		catch (Exception e)
		{
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
		
		UtilLog.d("Email name \"" + mail + "\" is email format: " + Boolean.valueOf(result).toString());
		return result;
	}
	
	public static String formatUrlEncoder(String data)
	{
		try{
    		if(data!=null && !data.equals("")){
    			return URLEncoder.encode(data, "UTF-8");
    		}else{
    			return "";
    		}
    	}catch(Exception ex){
    		UtilLog.d("Convert URL Encoder error : " + ex.getMessage());	
    		return data;
    	}
	}
	
	public static boolean isJsonFormat(String data)
	{
		boolean result = false;
		if(data.startsWith("{") && data.endsWith("}"))
        {
        	result = true;
        }
		return result;
	}
	
	public static boolean isXmlFormat(String data)
	{
		boolean result = false;
		if(data.startsWith("<") && data.endsWith(">"))
		{
			result = true;
		}
		return result;
	}
}
