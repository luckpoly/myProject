package com.goodfood86.tiaoshi.order121Project.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;


/**
 * Created by 习惯 on 2016/1/21.
 */
public class SafeOkHttp {
    /**
     * 访问在认证机构有人证的https访问
     *
     * @param hostHome 需要访问的地址
     * @return OkHttpClient
     */
    public static OkHttpClient getUnsafeOkHttpClient(final String hostHome) {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    if (hostname.equals(hostHome))
                        return true;
                    else
                        return false;
                }
            }).build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取带有制定输入流的认证证书的 OkHttpClient
     * 构造CertificateFactory对象，通过它的generateCertificate(is)方法得到Certificate。
     * 然后讲得到的Certificate放入到keyStore中。
     * 接下来利用keyStore去初始化我们的TrustManagerFactory
     * 由trustManagerFactory.getTrustManagers获得TrustManager[]初始化我们的SSLContext
     * 最后，设置我们mOkHttpClient.setSslSocketFactory即可。
     *
     * @param certificates 证书输入流
     * @return OkHttpClient
     * @throws Exception
     */
    public static OkHttpClient setCertificates(final URL url, InputStream... certificates) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        int index = 0;
        for (InputStream is : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, factory.generateCertificate(is));
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SSLContext sslContent = SSLContext.getInstance("TLS");
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        sslContent.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(sslContent.getSocketFactory()).hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                if (hostname.equals(url.getHost())) {
                    return true;
                }
                return false;
            }
        }).build();
        return client;
    }

    /**
     * 双向证书验证
     * 详见：Https的证书生成和相关代码
     * 获取带有制定输入流的认证证书的 OkHttpClient
     *
     * @param certificates 证书输入流
     * @return OkHttpClient
     * @throws Exception
     */
    public static OkHttpClient setCertificatesTwo(final URL url, InputStream keyClient, String password, InputStream... certificates) throws Exception {
        //初始化公钥
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        int index = 0;
        for (InputStream is : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, factory.generateCertificate(is));
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        //初始化Android端的密钥
        KeyStore keyClientStore = KeyStore.getInstance("PKCS12");
        keyClientStore.load(keyClient, password.toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyClientStore, password.toCharArray());
//        KeyManager keyManager = new MyX509KeyManager(keyClientStore,password,keyManagerFactory.getKeyManagers());
//        KeyManager[] keyManagerArray = keyManagerFactory.getKeyManagers();
//        keyManagerArray[0] = keyManager;

        SSLContext sslContent = SSLContext.getInstance("TLS");
        sslContent.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(sslContent.getSocketFactory()).hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                if (hostname.equals(url.getHost())) {
                    return true;
                }
                return false;
            }
        }).build();
        return client;
    }
}
