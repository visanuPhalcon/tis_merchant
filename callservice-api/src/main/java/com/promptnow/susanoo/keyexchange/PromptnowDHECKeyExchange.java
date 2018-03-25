// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 20/6/2014 18:03:59
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PromptnowDHKeyExchange.java

package com.promptnow.susanoo.keyexchange;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.KeyAgreement;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import android.annotation.SuppressLint;
import com.promptnow.utility.UtilLog;

import com.promptnow.network.secure.BaseSecureProvider;

public final class PromptnowDHECKeyExchange extends BaseSecureProvider
{
	private static BigInteger ec_FieldP = new BigInteger("fffffffffffffffffffffffffffffffeffffffffffffffff", 16);
    private static BigInteger ec_A = new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16);
    private static BigInteger ec_B = new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16);

    private static BigInteger ecPoint_X = new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16);
    private static BigInteger ecPoint_Y = new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16);
    private static BigInteger ecOrder_N = new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16);

    private static int ec_Cofactor = 1;

    private static String standardECCurveName = "brainpoolP256r1";

    public PromptnowDHECKeyExchange()
    {
    	super();
    	
    }

	@SuppressLint("TrulyRandom")
	@Override
	public boolean generateShareSecret(String buddyPublicKey) {
		// TODO Auto-generated method stub
		boolean result = false;
		
		try
		{
			BouncyCastleProvider provider = new BouncyCastleProvider();
			KeyFactory kf = KeyFactory.getInstance("ECDH", provider);
	        PublicKey prv_recovered = kf.generatePublic(new X509EncodedKeySpec(Hex.decodeHex(buddyPublicKey.toCharArray())));
	        
	        myAgreement = KeyAgreement.getInstance("ECDH", provider);
	        myAgreement.init(myKeyPair.getPrivate());
	        myAgreement.doPhase(prv_recovered, true);
	        byte[] shareSecret = myAgreement.generateSecret();
	        setSecretKey(shareSecret);
	        String shareSecretInHexString = new String(Hex.encodeHex(shareSecret));
	        result = true;
	        UtilLog.d("ShareSecrete: " + shareSecretInHexString);
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
	        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDH", new org.bouncycastle.jce.provider.BouncyCastleProvider());
	
	        if(standardECCurveName!=null)
	        {
	            //ECGenParameterSpec ecgps = new ECGenParameterSpec(standardECCurveName);
	            ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(standardECCurveName);
	            keyGen.initialize(ecSpec, new SecureRandom());
	        }
	        else
	        {
	            EllipticCurve curve = new EllipticCurve(new ECFieldFp(ec_FieldP), ec_A, ec_B);
	
	            ECParameterSpec ecSpec = new ECParameterSpec(curve, new ECPoint(ecPoint_X, ecPoint_Y), ecOrder_N, ec_Cofactor);
	
	            keyGen.initialize(ecSpec, new SecureRandom());
	        }

	        myKeyPair = keyGen.generateKeyPair();
	        result = true;
		}
		catch(Exception e)
		{
			UtilLog.e(UtilLog.getStackTraceString(e));
		}
		
		return result;
	}
}