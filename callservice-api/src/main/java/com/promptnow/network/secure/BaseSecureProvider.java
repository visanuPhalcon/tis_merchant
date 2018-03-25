// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 20/6/2014 18:03:59
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PromptnowDHKeyExchange.java

package com.promptnow.network.secure;

import java.security.KeyPair;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.promptnow.utility.UtilLog;

public abstract class BaseSecureProvider
{
	//private static final String HEX = "0123456789ABCDEF";
	
    private byte encryptKey[];
	private byte decryptKey[];
	private byte secretKey[];
	
    protected KeyPair myKeyPair;
    protected KeyAgreement myAgreement;
    
    public BaseSecureProvider()
    {    	
        encryptKey = new byte[32];
        decryptKey = new byte[32];
        secretKey = null;
    }

    public abstract boolean generateShareSecret(String buddyPublicKey);

	public abstract boolean generateMyKeyPair();

    public String getMyPublicKeyInDecString()
    {
        String decPublicKey = "";
        try
        {
        	DHPublicKey key = (DHPublicKey)myKeyPair.getPublic();
            return key.getY().toString(10);
        }
        catch(Exception e)
        {
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
        return decPublicKey;
    }
    
    public String getMyPublicKeyInHexString()
    {
    	//byte[] pk = ((DHPublicKey)(myKeyPair.getPublic())).getEncoded();
    	byte[] pk = myKeyPair.getPublic().getEncoded();
    	
        return new String(Hex.encodeHex( pk ));
    }
/*
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

    public static String getHexStringGiar(byte b[]) throws Exception
    {
        String result = "";
        if(b != null)
        {
        	StringBuilder builder = new StringBuilder();
            for(int i = 0; i < b.length; i++)
            {
            	String str = Integer.toString((b[i] & 0xff) + 256, 16).substring(1);
                builder.append(str);
            }
            
            result = builder.toString();
        }
        return result;
    }
*/
    public static byte[] getEncrypted(byte plaintext[], byte keyencrypt[], byte iv[])
    {
        byte encryptData[] = (byte[])null;
        try
        {
            String request = new String(plaintext);
            //String data_request = replace(request, "&", "&amp;");
            SecretKeySpec skeySpec = new SecretKeySpec(keyencrypt, "AES/CBC/PKCS5Padding");
            IvParameterSpec ivparameter = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, skeySpec, ivparameter);
            encryptData = cipher.doFinal(request.getBytes());
            return encryptData;
        }
        catch(Exception e)
        {
			UtilLog.e(UtilLog.getStackTraceString(e));
        }
        return null;
    }

    public static byte[] getDecrypted(byte plaintext[], byte keydecrypt[], byte iv[])
    {
        byte decryptData[] = (byte[])null;
        try
        {
            byte input[] = plaintext;
            SecretKeySpec skeySpec = new SecretKeySpec(keydecrypt, "AES/CBC/PKCS5Padding");
            IvParameterSpec ivparameter = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, skeySpec, ivparameter);
            decryptData = cipher.doFinal(input);
            return decryptData;
        }
        catch(Exception e)
        {
			UtilLog.e(UtilLog.getStackTraceString(e));
            return plaintext;
        }
    }
/*
    public static byte[] toByte(String hexString)
    {
        int len = hexString.length() / 2;
        byte result[] = new byte[len];
        for(int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();

        return result;
    }

    public static String toHex(byte buf[], String encode)
    {
        if(buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for(int i = 0; i < buf.length; i++)
            appendHex(result, buf[i]);

        try
        {
            return new String(buf, encode);
        }
        catch(UnsupportedEncodingException e)
        {
			UtilLog.e(UtilLog.getStackTraceString(e));
        }
        return fromHex(result.toString());
    }

    public static String toHex(byte buf[])
    {
        if(buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for(int i = 0; i < buf.length; i++)
            appendHex(result, buf[i]);

        String str = fromHex(result.toString());
        str = replace(str, "&quot;", "\"");
        str = replace(str, "\n", " ");
        str = replace(str, "\r", " ");
        str = replace(str, "&apos;", " '");
        str = replace(str, "&amp;", "&");
        str = replace(str, "&lt;", "lt;");
        str = replace(str, "&gt;", "gt;");
        return str;
    }

    public static String fromHex(String hex)
    {
        return new String(toByte(hex));
    }

    private static void appendHex(StringBuffer sb, byte b)
    {
        sb.append(HEX.charAt(b >> 4 & 0xf)).append(HEX.charAt(b & 0xf));
    }

    public static String replace(String _text, String _searchStr, String _replacementStr)
    {
        StringBuffer sb = new StringBuffer();
        int searchStringPos = _text.indexOf(_searchStr);
        int startPos = 0;
        int searchStringLength = _searchStr.length();
        for(; searchStringPos != -1; searchStringPos = _text.indexOf(_searchStr, startPos))
        {
            sb.append(_text.substring(startPos, searchStringPos)).append(_replacementStr);
            startPos = searchStringPos + searchStringLength;
        }

        sb.append(_text.substring(startPos, _text.length()));
        return sb.toString();
    }
 */   
    public byte[] getEncryptKey()
    {
		return encryptKey;
	}

	public byte[] getDecryptKey()
	{
		return decryptKey;
	}

	public void setSecretKey(byte[] secretKey)
	{
		this.secretKey = secretKey;
		
		int startbit = 0;
		if(this.secretKey.length > 64)
		{
			startbit = this.secretKey.length - 64;
		}
		if(this.secretKey.length > 63)
		{
			for(int i = 0; i < encryptKey.length; i++)
			{
				encryptKey[i] = this.secretKey[2 * i + 1 + startbit];
				decryptKey[i] = this.secretKey[(62 - 2 * i) + startbit];
			}
		}
		else if(this.secretKey.length == 63)
		{
			for(int i = 1; i < encryptKey.length - 1; i++)
			{
				encryptKey[i] = this.secretKey[2 * i + 1];
				decryptKey[i] = this.secretKey[62 - 2 * i];
			}

			encryptKey[0] = this.secretKey[1];
			decryptKey[31] = this.secretKey[1];
			encryptKey[31] = -1;
			decryptKey[0] = -1;
		}
		else if(this.secretKey.length == 32)
		{
			encryptKey = secretKey;
			decryptKey = secretKey;
		}
	}
}