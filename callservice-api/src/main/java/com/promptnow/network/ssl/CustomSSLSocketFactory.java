package com.promptnow.network.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class CustomSSLSocketFactory extends SSLSocketFactory {

    SSLContext sslContext = SSLContext.getInstance("TLS");

    public CustomSSLSocketFactory()
            throws NoSuchAlgorithmException, KeyManagementException,
            KeyStoreException, UnrecoverableKeyException {
        super();

        TrustManager tm = new CustomX509TrustManager();

        sslContext.init(null, new TrustManager[] { tm }, null);
    }

    public CustomSSLSocketFactory(SSLContext context)
            throws KeyManagementException, NoSuchAlgorithmException,
            KeyStoreException, UnrecoverableKeyException {
        super();
        sslContext = context;
    }

    private void adjustSocket(Socket socket)
    {
        String[] cipherSuites = ((SSLSocket) socket).getSSLParameters().getCipherSuites();
        ArrayList<String> cipherSuiteList = new ArrayList<String>(Arrays.asList(cipherSuites));

        cipherSuiteList.add("TLS_RSA_WITH_3DES_EDE_CBC_SHA");
        cipherSuites = cipherSuiteList.toArray(new String[cipherSuiteList.size()]);
        ((SSLSocket) socket).getSSLParameters().setCipherSuites(cipherSuites);

        String[] protocols = ((SSLSocket) socket).getSSLParameters().getProtocols();
        ArrayList<String> protocolList = new ArrayList<String>(Arrays.asList(protocols));

        for (int ii = protocolList.size() - 1; ii >= 0; --ii )
        {
            if ((protocolList.get(ii).contains("SSLv3")) || (protocolList.get(ii).contains("TLSv1.1")) || (protocolList.get(ii).contains("TLSv1.2")))
                protocolList.remove(ii);
        }

        protocols = protocolList.toArray(new String[protocolList.size()]);
        ((SSLSocket)socket).setEnabledProtocols(protocols);
    }

	@Override
	public String[] getDefaultCipherSuites() {
		// TODO Auto-generated method stub
		return sslContext.getSocketFactory().getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		// TODO Auto-generated method stub
		return sslContext.getSocketFactory().getSupportedCipherSuites();
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException,
			UnknownHostException {
		// TODO Auto-generated method stub
		Socket socket = sslContext.getSocketFactory().createSocket(host, port);
        ((SSLSocket) socket).setEnabledCipherSuites(((SSLSocket) socket).getSupportedCipherSuites());
        adjustSocket(socket);
        return socket;
	}

	@Override
	public Socket createSocket(String host, int port, InetAddress localHost,
			int localPort) throws IOException, UnknownHostException {
		// TODO Auto-generated method stub
		Socket socket = sslContext.getSocketFactory().createSocket();
        ((SSLSocket) socket).setEnabledCipherSuites(((SSLSocket) socket).getSupportedCipherSuites());
        adjustSocket(socket);
        return socket;
	}

	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		// TODO Auto-generated method stub
		Socket socket = sslContext.getSocketFactory().createSocket(host, port);
        ((SSLSocket) socket).setEnabledCipherSuites(((SSLSocket) socket).getSupportedCipherSuites());
        adjustSocket(socket);
        return socket;
	}

	@Override
	public Socket createSocket(InetAddress address, int port,
			InetAddress localAddress, int localPort) throws IOException {
		// TODO Auto-generated method stub
		Socket socket = sslContext.getSocketFactory().createSocket(address, port, localAddress, localPort);
        ((SSLSocket) socket).setEnabledCipherSuites(((SSLSocket) socket).getSupportedCipherSuites());
        adjustSocket(socket);
        return socket;
	}

	@Override
	public Socket createSocket(Socket s, String host, int port,
			boolean autoClose) throws IOException {
		// TODO Auto-generated method stub
		Socket socket = sslContext.getSocketFactory().createSocket(s, host, port, autoClose);
        ((SSLSocket) socket).setEnabledCipherSuites(((SSLSocket) socket).getSupportedCipherSuites());
        adjustSocket(socket);
        return socket;
	}
}
