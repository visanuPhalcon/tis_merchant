package com.promptnow.bean;

import java.security.PublicKey;

import com.promptnow.utility.UtilCipher;

public class HardCodeDecryptor {

	//privateexponent = "19258922361205814638977520048218114297237511563532710580915832000258706777125551166177971266627849831752771863774606391372104451978374958729191043061712622115632409959961346294326062415900689214530548648111893595695664358586755131396671001932468445089137556643274387768405243215129943864994603495231429913549"
	private static String modulus = "148543249945433149816955364410976880863605366051458544585321982087908069442447598773426586195244132567209769287536351544233683590597476717692714297885778548041928735865581530159168080533697774915622668334899543791312777493585583075551890194590621665281875552756311228996330875478270693527290056607835842390469";
	private static String exponent = "65537";
	private static HardCodeDecryptor decryptor = null;
	
	private PublicKey key = null;
	
	public static HardCodeDecryptor getInstance()
	{
		if(decryptor == null)
		{
			decryptor = new HardCodeDecryptor();
		}
		
		return decryptor;
	}
	
	public HardCodeDecryptor()
	{
		key = UtilCipher.getPublicKey(modulus, exponent);
	}
	
	public String getDecryptedValue(String data)
	{
		String decryptedValue = data;
		byte[] encrypted = UtilCipher.hexStringToByteArray(data);
		byte[] decrypted = null;
		decrypted = UtilCipher.getDecryptedRSA(encrypted, key);
		decryptedValue = new String(decrypted);
		
		return decryptedValue;
	}
}
