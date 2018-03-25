package com.promptnow.utility;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.promptnow.susanoo.PromptnowProperties;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class UtilNetwork extends BaseUtility {
	private static Principal[] principalIssuer;
	private static Principal[] principalSubject;
	private static String[] publicKey;

	private interface iVerifyHostNameCertificate
	{
		public boolean OnVerifyHostNameCertificate(String cn_issuer, String cn_subject, String cn_publicKey);
	}
	
	private class PromptnowHostnameVerifier implements HostnameVerifier
	{
		private iVerifyHostNameCertificate handler = null;
		
		public PromptnowHostnameVerifier(iVerifyHostNameCertificate verifyHandler)
		{
			handler = verifyHandler;
		}
		
		public boolean verify(String hostname, SSLSession session)
		{
			for (int i = 0; i < principalIssuer.length; i++)
			{
				String cn_issuer = "";
				String cn_subject = "";
				String cn_publicKey = "";

				String[] split = principalIssuer[i].getName().split(",");
				for (String x : split)
				{
					if (x.contains("CN="))
					{
						cn_issuer = x.trim();
						cn_issuer = cn_issuer.substring(cn_issuer.indexOf("CN=") + 3);
					}
				}

				String[] splits = principalSubject[i].getName().split(",");
				for (String x : splits)
				{
					if (x.contains("CN="))
					{
						cn_subject = x.trim();
						cn_subject = cn_subject.substring(cn_subject.indexOf("CN=") + 3);
					}
				}

				cn_publicKey = publicKey[i];

				UtilLog.d("Certificate: " + i + ". cn_publicKey: " + cn_publicKey);
				UtilLog.d("Certificate: " + i + ". cn_issuer: " + cn_issuer);
				UtilLog.d("Certificate: " + i + ". cn_subject: " + cn_subject);

				if (handler.OnVerifyHostNameCertificate(cn_issuer, cn_subject, cn_publicKey))
				{
					UtilLog.i("Certificate: " + "Verify Hostname is prove");
					return true;
				}
					   
				if(checkGoogleMapCertificate(cn_issuer, cn_subject))
				{
					return true;
				}
			}

			UtilLog.i("Certificate: " + "Verify Hostname is suspicious");
			return false;
		}
	}
	
	@SuppressLint("TrulyRandom")
	private static void doVerifyHostNameCertificate(final iVerifyHostNameCertificate verifier)
	{
		TrustManager trustManager = new X509TrustManager()
		{
			public java.security.cert.X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{
				principalIssuer = new Principal[certs.length];
				principalSubject = new Principal[certs.length];
				publicKey = new String[certs.length];
				   
				for (int i = 0; i < certs.length; i++)
				{
					principalIssuer[i] = certs[i].getIssuerDN();
					principalSubject[i] = certs[i].getSubjectDN();

					try
					{
						publicKey[i] = UtilCipher.getHexString(certs[i].getPublicKey().getEncoded());
					}
					catch(Exception e)
					{
						publicKey[i] = "";
						UtilLog.e(UtilLog.getStackTraceString(e));
					}
					   
					UtilLog.v("Certificate PublicKey: " + publicKey[i]);
					UtilLog.v("Certificate Issuer: " + principalIssuer[i].getName());
					UtilLog.v("Certificate Subject: " + principalSubject[i].getName());
			   }
		   }
	   };
		
	   TrustManager[] trustManagers = {	trustManager };
	   HttpsURLConnection.setDefaultHostnameVerifier((new UtilNetwork()).new PromptnowHostnameVerifier(verifier));
	   try
	   {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustManagers, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	   }
	   catch (Exception e)
	   {
			UtilLog.e(UtilLog.getStackTraceString(e));
	   }
	}
	
	public static void setDefaultUrlCertificate(final String cert_Issuer, final String cert_Subject) {

		doVerifyHostNameCertificate(new iVerifyHostNameCertificate() {
			@Override
			public boolean OnVerifyHostNameCertificate(String cn_issuer, String cn_subject, String cn_publicKey) {
				// TODO Auto-generated method stub
				return cert_Subject.equalsIgnoreCase(cn_subject) && cert_Issuer.equalsIgnoreCase(cn_issuer);
			}
		});
   	}

	@SuppressLint("TrulyRandom")
	public static void setDefaultUrlCertificate(final String cert_PublicKey) {

		doVerifyHostNameCertificate(new iVerifyHostNameCertificate() {			
			@Override
			public boolean OnVerifyHostNameCertificate(String cn_issuer, String cn_subject, String cn_publicKey) {
				// TODO Auto-generated method stub
				return cert_PublicKey.equals(cn_publicKey);
			}
		});
	}

	public static TrustManager getTrustAllManager()
	{
		TrustManager trustManager = new X509TrustManager()
		{
			public java.security.cert.X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{
				Principal[] local_principalIssuer = new Principal[certs.length];
				Principal[] local_principalSubject = new Principal[certs.length];
				String[] local_publicKey = new String[certs.length];
				   
				for (int i = 0; i < certs.length; i++)
				{
					local_principalIssuer[i] = certs[i].getIssuerDN();
					local_principalSubject[i] = certs[i].getSubjectDN();

					try
					{
						local_publicKey[i] = UtilCipher.getHexString(certs[i].getPublicKey().getEncoded());
					}
					catch(Exception e)
					{
						local_publicKey[i] = "";
						UtilLog.e(UtilLog.getStackTraceString(e));
					}
					   
					UtilLog.v("Certificate PublicKey: " + local_publicKey[i]);
					UtilLog.v("Certificate Issuer: " + local_principalIssuer[i].getName());
					UtilLog.v("Certificate Subject: " + local_principalSubject[i].getName());
			   }
		   }
	   };
	   
	   return trustManager;
	}
	
	public static boolean isOnline()
	{
		ConnectivityManager cm = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	public static Map<String, String> getHttpHeaderForSynchronizedCookiesWebView()
	{
		HashMap<String, String> header = new HashMap<String, String>();

		header.put("Cookie", PromptnowProperties.COOKIE);
		header.put("Content-Type", "text/html");
		header.put("accept-charset", "UTF-8");
		header.put("PlatForm", PromptnowProperties.APPLICATION_TYPE);
		header.put("http.keepAlive", "true");
		header.put("Pragma", "no-cache");
		header.put("Cache-Control", "no-cache");
		header.put("Expires", "-1");
		header.put("User-Agent", PromptnowProperties.USER_AGENT);
		return header;
	}

	private static boolean checkGoogleMapCertificate(String issuer, String subject)
	{
		boolean result = false;

		if(sert_google_name.equalsIgnoreCase(subject) && sert_google_root.equalsIgnoreCase(issuer))
		{
			result = true;
		}

		return result;
	}

	private static String sert_google_name = "*.google.com";
	private static String sert_google_root = "Google Internet Authority G2";
}
