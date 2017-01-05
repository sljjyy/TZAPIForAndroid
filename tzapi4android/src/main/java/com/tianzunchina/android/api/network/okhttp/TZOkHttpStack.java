package com.tianzunchina.android.api.network.okhttp;


import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.OkUrlFactory;

/**
 * 对OKHttp进行HttpStack封装 以供Volley使用
 * CraetTime 2016-3-23
 * @author SunLiang
 *  use okhttp-urlconnection
 * 引自：https://gist.github.com/bryanstern/4e8f1cb5a8e14c202750
 */
public class TZOkHttpStack extends HurlStack {
    private final OkUrlFactory okUrlFactory;

    public TZOkHttpStack() {
        this(new OkUrlFactory(getUnsafeOkHttpClient()));
    }

    public TZOkHttpStack(OkUrlFactory okUrlFactory) {
        if (okUrlFactory == null) {
            throw new NullPointerException("Client must not be null.");
        }
        this.okUrlFactory = okUrlFactory;
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return okUrlFactory.open(url);
    }

    /**
     * 支持HTTPS。按下面的注释进行
     *
     * @return
     */
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            /**
             * 读取公钥证书内容
             * 首先要把你的https公钥证书通过浏览器或者其他方法导出，放进android资源目录assets下。
             * 然后AppConfig这个类是继承了Application的，android启动的时候会先执行它，getApp是一个单例模式的实现
             */

            // 配置HTTPS时需要打开下面的注释


            // InputStream inputStream = AppConfig.getApp().getAssets().open("https.cer");
            //
            // CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //
            // KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // keyStore.load(null);
            //
            // int index = 0;
            // String certificateAlias = Integer.toString(index++);
            // keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(inputStream));

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());

            // 配置HTTPS时需要把下面的注释打开
            // trustManagerFactory.init(keyStore);

            // 配置HTTPS时需要把下面这句注释掉
            trustManagerFactory.init((KeyStore) null);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .build();
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}