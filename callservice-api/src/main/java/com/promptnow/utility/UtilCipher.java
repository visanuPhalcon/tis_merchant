package com.promptnow.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;

public class UtilCipher {

    public static byte[] hexStringToByteArray(String s)
    {
        int len = s.length();
        byte data[] = new byte[len / 2];
        for(int i = 0; i < len; i += 2)
            data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));

        return data;
    }

    public static String getHexString(byte b[])
        throws Exception
    {
        String result = "";
        if(b != null)
        {
            for(int i = 0; i < b.length; i++)
                result = (new StringBuilder(String.valueOf(result))).append(Integer.toString((b[i] & 0xff) + 256, 16).substring(1)).toString();

        }
        return result;
    }
	
	@SuppressLint("TrulyRandom")
	public static byte[] getEncryptedAES256(byte decryptedData[], byte key[], byte iv[])
	{
		byte[] result = null;

		try
		{
			Cipher c = Cipher.getInstance("AES");
			SecretKeySpec k = new SecretKeySpec(key, "AES");
			IvParameterSpec ivParameter = new IvParameterSpec(iv);
			c.init(Cipher.ENCRYPT_MODE, k, ivParameter);
			result = c.doFinal(decryptedData);
		}
		catch(Exception e)
		{
			UtilLog.e(UtilLog.getStackTraceString(e));
		}
			
		return result;
	}
	
	public static byte[] getDecryptedAES256(byte encryptedData[], byte key[], byte iv[])
	{
		byte[] result = null;
		
		try
		{
			Cipher c = Cipher.getInstance("AES");
			SecretKeySpec k = new SecretKeySpec(key, "AES");
			IvParameterSpec ivParameter = new IvParameterSpec(iv);
			c.init(Cipher.DECRYPT_MODE, k, ivParameter);
			result = c.doFinal(encryptedData);
		}
		catch(Exception e)
		{
			UtilLog.e(UtilLog.getStackTraceString(e));
		}
		
		return result;
	}

	public static byte[] getEncryptedRSA(byte decryptedData[], Key key)
	{
		byte[] result = null;

		try
		{
			Cipher c = Cipher.getInstance("RSA");
	        c.init(Cipher.ENCRYPT_MODE, key);
			result = c.doFinal(decryptedData);
		}
		catch(Exception e)
		{
			UtilLog.e(UtilLog.getStackTraceString(e));
		}
			
		return result;
	}
	
	public static byte[] getDecryptedRSA(byte encryptedData[], Key key)
	{
		byte[] result = null;
		
		try
		{
			Cipher c = Cipher.getInstance("RSA");
	        c.init(Cipher.DECRYPT_MODE, key);
			result = c.doFinal(encryptedData);
		}
		catch(Exception e)
		{
			UtilLog.e(UtilLog.getStackTraceString(e));
		}
		
		return result;
	}
	
	/** 
	 * read Public Key From File 
	 * @param fileName 
	 * @return PublicKey 
	 * @throws IOException 
	 */  
	public static PublicKey readPublicKeyFromFile(String fileName) throws IOException
	{  
		FileInputStream fis = null;  
		ObjectInputStream ois = null;  
		try 
		{  
			fis = new FileInputStream(new File(fileName));  
			ois = new ObjectInputStream(fis);  
    
			BigInteger modulus = (BigInteger) ois.readObject();  
			BigInteger exponent = (BigInteger) ois.readObject();
    
			PublicKey publicKey = getPublicKey(modulus, exponent);
             
			return publicKey;  
       
		} 
		catch (Exception e) 
		{
			UtilLog.e(UtilLog.getStackTraceString(e));
		}  
		finally
		{  
			if(ois != null)
			{  
				ois.close();  
				if(fis != null)
				{  
					fis.close();  
				}  
			}  
		}  
		return null;  
	}  
	      
	/** 
	 * read Public Key From File 
	 * @param fileName 
	 * @return 
	 * @throws IOException 
	 */  
    public static PrivateKey readPrivateKeyFromFile(String fileName) throws IOException
    {  
		FileInputStream fis = null;  
		ObjectInputStream ois = null;  
		try 
		{  
			fis = new FileInputStream(new File(fileName));  
			ois = new ObjectInputStream(fis);  
		    
			BigInteger modulus = (BigInteger) ois.readObject();  
			BigInteger exponent = (BigInteger) ois.readObject();  
		      
			PrivateKey privateKey = getPrivateKey(modulus, exponent);  
		             
			return privateKey;     
		}
		catch (Exception e) 
		{  
			UtilLog.e(UtilLog.getStackTraceString(e));  
		}  
		finally
		{  
			if(ois != null)
			{  
				ois.close();  
				if(fis != null)
				{  
					fis.close();  
				}  
			}  
		}  
		return null;  
	}
    
    public static KeyPair generateRSAKeyPair(int keyLength)
    {
    	KeyPair result = null;
    	
	    try 
	    {
	    	KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
	        kpg.initialize(keyLength);
	        result = kpg.genKeyPair();
	        
	    } catch (Exception e) 
	    {
	        UtilLog.e(UtilLog.getStackTraceString(e));
	    }
	    
	    return result;
    }
     
 	public static PublicKey getPublicKey(String strModulus, String strExponent)
 	{
 		PublicKey key = null;
 		BigInteger modulus = new BigInteger(strModulus);  
 		BigInteger exponent = new BigInteger(strExponent);
     
 		key = getPublicKey(modulus, exponent);

 		return key;  
 	}  

 	public static PrivateKey getPrivateKey(String strModulus, String strExponent)
 	{  
 		PrivateKey key = null;
 		BigInteger modulus = new BigInteger(strModulus);  
 		BigInteger exponent = new BigInteger(strExponent);
     
 		key = getPrivateKey(modulus, exponent);
 		
 		return key;  
 	}

 	public static PublicKey getPublicKey(BigInteger modulus, BigInteger exponent)
 	{
 		PublicKey key = null;
     
 		try
 		{
			//Get Public Key  
			RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);  
			KeyFactory fact = KeyFactory.getInstance("RSA");  
			key = fact.generatePublic(rsaPublicKeySpec);
 		}
 		catch(Exception e)
 		{
 			UtilLog.e(UtilLog.getStackTraceString(e));
 		}

 		return key;  
 	}  

 	public static PrivateKey getPrivateKey(BigInteger modulus, BigInteger exponent)
 	{  
 		PrivateKey key = null;
     
 		try
 		{
 			//Get Private Key  
 			RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);  
 			KeyFactory fact = KeyFactory.getInstance("RSA");  
 			key = fact.generatePrivate(rsaPrivateKeySpec);  
     
 		}
 		catch (Exception e) 
 		{  
 			UtilLog.e(UtilLog.getStackTraceString(e));  
 		}
 		
 		return key;  
 	}
}
