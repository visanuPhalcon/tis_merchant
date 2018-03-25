// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 20/6/2014 18:03:59
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PromptnowDHKeyExchange.java

package com.promptnow.susanoo.keyexchange;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import android.annotation.SuppressLint;
import com.promptnow.bean.ConfigurationsManager;
import com.promptnow.utility.UtilLog;

import com.promptnow.network.secure.BaseSecureProvider;

public final class PromptnowDHKeyExchange extends BaseSecureProvider
{

    private BigInteger g;
    private BigInteger p;
    private DHParameterSpec dhParamSpec;

    public PromptnowDHKeyExchange()
    {
    	super();
    	String modulus = ConfigurationsManager.getInstance().getConfiguration("dh.modulus");
    	String exponent = ConfigurationsManager.getInstance().getConfiguration("dh.exponent");
    	UtilLog.d("modulus: " + modulus);
    	UtilLog.d("exponent: " + exponent);
    	p = new BigInteger(modulus);
    	g = new BigInteger(exponent);
    }
    
	@SuppressLint("TrulyRandom")
	@Override
	public boolean generateShareSecret(String buddyPublicKey) {
		// TODO Auto-generated method stub
		boolean result = false;
		
    	try
        {
    		BigInteger buddyBigInt = new BigInteger(buddyPublicKey);
    		BouncyCastleProvider provider = new BouncyCastleProvider();
			KeyFactory kf = KeyFactory.getInstance("DH", provider);
			DHPublicKeySpec publicKeySpec = new DHPublicKeySpec(buddyBigInt, p, g);
	        PublicKey prv_recovered = kf.generatePublic(publicKeySpec);
	        
	        myAgreement = KeyAgreement.getInstance("DH", provider);
	        myAgreement.init(myKeyPair.getPrivate());
	        myAgreement.doPhase(prv_recovered, true);
	        byte[] secretKey = myAgreement.generateSecret();
	        setSecretKey(secretKey);
	        String secretKeyInHexString = new String(Hex.encodeHex(secretKey));
	        result = true;
	        
            UtilLog.d("ShareSecret: " + secretKeyInHexString);
        }
        catch(Exception e)
        {
			UtilLog.e(UtilLog.getStackTraceString(e));
        }
    	
		return result;
	}

	@Override
	public boolean generateMyKeyPair() {
		// TODO Auto-generated method stub
		boolean result = false;
		
		try
		{
			dhParamSpec = new DHParameterSpec(p, g);
	        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DH","BC");
	        keyPairGen.initialize(dhParamSpec);
	        myKeyPair = keyPairGen.generateKeyPair();
	        result = true;
	        
	        UtilLog.d("public key: " + getMyPublicKeyInDecString());
		}
		catch(Exception e)
		{
			UtilLog.e(UtilLog.getStackTraceString(e));
		}
		return result;
	}
}