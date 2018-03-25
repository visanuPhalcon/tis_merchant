package com.promptnow.network.ssl;

import java.security.cert.CertificateException;

import javax.net.ssl.X509TrustManager;

public class CustomX509TrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
			String authType) throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
			String authType) throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}
}
