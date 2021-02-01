package com.springboot.product.utile;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class HttpUtil {

    /**
     * 请求时间等待时间较短
     *
     * @param hostname 代理服务器IP
     * @param port     代理服务器端口
     * @return 请求配置信息
     */
    public static RequestConfig requestConfigFast(String hostname, int port) {
        return requestConfig(hostname, port, 10000, 10000, 10000, true);
    }

    /**
     * 请求时间等待时间较长
     *
     * @param hostname 代理服务器IP
     * @param port     代理服务器端口
     * @return 请求配置信息
     */
    public static RequestConfig requestConfigSlow(String hostname, int port) {
        return requestConfig(hostname, port, 60000, 120000, 60000, true);
    }

    /**
     * 请求配置信息
     *
     * @param hostname                 代理服务器IP
     * @param port                     代理服务器端口
     * @param connectTimeout           连接超时时间
     * @param connectionRequestTimeout 请求超时时间
     * @param socketTimeout            socket读写超时时间
     * @param redirectsEnabled         是否允许重定向
     * @return 配置信息
     */
    public static RequestConfig requestConfig(String hostname, int port, int connectTimeout, int connectionRequestTimeout, int socketTimeout, Boolean redirectsEnabled) {
        // 配置信息
        RequestConfig requestConfig;
        if (!StringUtil.isEmpty(hostname)) {
            // 代理 服务器
            HttpHost proxy = new HttpHost(hostname, port);
            // 配置信息
            requestConfig = RequestConfig.custom()
                    // 代理服务器
                    .setProxy(proxy)
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(connectTimeout)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(socketTimeout)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(redirectsEnabled).build();
        } else {
            requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    .setSocketTimeout(socketTimeout)
                    .setRedirectsEnabled(redirectsEnabled).build();
        }
        return requestConfig;
    }

    /**
     * get 发送请求
     * @param url           请求地址
     * @param requestConfig 请求配置信息
     * @return 数据
     */
    public static String get(String url, RequestConfig requestConfig) {
        log("开始调用接口");
        log("接口地址: " + url);
        String result = null;
        // TODO 获得Http客户端 正式调用的时候改成true
        CloseableHttpClient httpClient = getHttpClient(false,false);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            // 由客户端执行(发送)get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            log("后台返回数据: " + result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * post 发送请求(http默认使用)
     * @param url           请求地址
     * @param requestConfig 请求配置信息
     * @param jsonParam     传入参数
     * @param jsonCharset   传入参数字符集
     * @param headerParam   请求header信息(请求格式，请求token)
     * @return 数据
     */
    public static String post(String url, RequestConfig requestConfig, String jsonParam, String jsonCharset, String headerParam) {
        return post(url, requestConfig, jsonParam, jsonCharset, headerParam, true,false);
    }

    /**
     * post 发送请求
     * @param url           请求地址
     * @param requestConfig 请求配置信息
     * @param jsonParam     传入参数
     * @param jsonCharset   传入参数字符集
     * @param headerParam   请求header信息(请求格式，请求token)
     * @param certificateValid  是否进行证书校验
     * @return 数据
     */
    public static String post(String url, RequestConfig requestConfig, String jsonParam, String jsonCharset, String headerParam,Boolean certificateValid,Boolean isHostNameValid) {
        log("开始调用接口");
        log("接口地址: " + url);
        log("请求头参数: " + headerParam);
        log("接口参数: " + jsonParam);
        String result = null;
        // TODO 获得Http客户端 正式调用的时候改成true
        CloseableHttpClient httpClient = getHttpClient(certificateValid,isHostNameValid);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if (jsonParam!=null) {
                StringEntity entity = new StringEntity(jsonParam, jsonCharset);
                // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
                httpPost.setEntity(entity);
            }
            JSONObject headerObject = JSONObject.parseObject(headerParam);
            // 循环取得header信息
            for (String str : headerObject.keySet()) {
                httpPost.setHeader(str, headerObject.getString(str));
            }
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            log("后台返回数据: " + result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String postForm(String url, RequestConfig requestConfig, List<NameValuePair> params, String jsonCharset, String headerParam) {
        log("开始调用接口");
        log("接口地址: " + url);
        log("请求头参数: " + headerParam);
//        log("接口参数: " + jsonParam);
        String result = null;
        // TODO 获得Http客户端 正式调用的时候改成true
        CloseableHttpClient httpClient = getHttpClient(false,false);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if (params!=null) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
                // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
                httpPost.setEntity(entity);
            }
            JSONObject headerObject = JSONObject.parseObject(headerParam);
            // 循环取得header信息
            for (String str : headerObject.keySet()) {
                httpPost.setHeader(str, headerObject.getString(str));
            }
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            log("后台返回数据: " + result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String post(String url, String jsonParam) {
        log("开始调用接口");
        log("接口地址: " + url);
        log("接口参数: " + jsonParam);
        String result = null;
        // TODO 获得Http客户端 正式调用的时候改成true
        CloseableHttpClient httpClient = getHttpClient(false,false);
        // 创建Post请求
        log("创建Post请求");
        HttpPost httpPost = new HttpPost(url);
        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(10000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(120000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(30000)
                // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);
        StringEntity entity = new StringEntity(jsonParam, "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            log("后台返回数据: " + result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据是否是https请求，获取HttpClient客户端
     * <p>
     * <p>
     * 提示: 此工具类的封装、相关客户端、服务端证书的生成，可参考我的这篇博客:
     * <linked>https://blog.csdn.net/justry_deng/article/details/91569132</linked>
     *
     * @param isHttps 是否是HTTPS请求
     * @return HttpClient实例
     * @date 2019/9/18 17:57
     */
    private static CloseableHttpClient getHttpClient(boolean isHttps, boolean isHostNameValid) {
        CloseableHttpClient httpClient;
        if (isHttps) {
            SSLConnectionSocketFactory sslSocketFactory;
            try {
                /// 如果不作证书校验的话
                sslSocketFactory = getSocketFactory(false, null, null);

                /// 如果需要证书检验的话
                // 证书
//                InputStream ca = ClassLoader.getSystemClassLoader().getResourceAsStream("client/ds.crt");
//                // 证书的别名，即:key。 注:cAalias只需要保证唯一即可，不过推荐使用生成keystore时使用的别名。
//                String cAalias = System.currentTimeMillis() + "" + new SecureRandom().nextInt(1000);
//                sslSocketFactory = getSocketFactory(true, ca, cAalias);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            HttpClientBuilder builder = HttpClientBuilder.create();
            if(isHostNameValid){
                httpClient = builder.setSSLSocketFactory(sslSocketFactory).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
            } else {
                httpClient = builder.setSSLSocketFactory(sslSocketFactory).build();
            }
            return httpClient;
        }
        httpClient = HttpClientBuilder.create().build();
        return httpClient;
    }

    /**
     * HTTPS辅助方法, 为HTTPS请求 创建SSLSocketFactory实例、TrustManager实例
     *
     * @param needVerifyCa  是否需要检验CA证书(即:是否需要检验服务器的身份)
     * @param caInputStream CA证书。(若不需要检验证书，那么此处传null即可)
     * @param cAalias       别名。(若不需要检验证书，那么此处传null即可)
     *                      注意:别名应该是唯一的， 别名不要和其他的别名一样，否者会覆盖之前的相同别名的证书信息。别名即key-value中的key。
     * @return SSLConnectionSocketFactory实例
     * @throws NoSuchAlgorithmException 异常信息
     * @throws CertificateException     异常信息
     * @throws KeyStoreException        异常信息
     * @throws IOException              异常信息
     * @throws KeyManagementException   异常信息
     * @date 2019/6/11 19:52
     */
    private static SSLConnectionSocketFactory getSocketFactory(boolean needVerifyCa, InputStream
            caInputStream, String cAalias)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException,
            IOException, KeyManagementException {
        X509TrustManager x509TrustManager;
        // https请求，需要校验证书
        if (needVerifyCa) {
            KeyStore keyStore = getKeyStore(caInputStream, cAalias);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            x509TrustManager = (X509TrustManager) trustManagers[0];
            // 这里传TLS或SSL其实都可以的
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
            return new SSLConnectionSocketFactory(sslContext);
        }
        // https请求，不作证书校验
        x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                // 不验证
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
        return new SSLConnectionSocketFactory(sslContext);
    }

    /**
     * 获取(密钥及证书)仓库
     * 注:该仓库用于存放 密钥以及证书
     *
     * @param caInputStream CA证书(此证书应由要访问的服务端提供)
     * @param cAalias       别名
     *                      注意:别名应该是唯一的， 别名不要和其他的别名一样，否者会覆盖之前的相同别名的证书信息。别名即key-value中的key。
     * @return 密钥、证书 仓库
     * @throws KeyStoreException        异常信息
     * @throws CertificateException     异常信息
     * @throws IOException              异常信息
     * @throws NoSuchAlgorithmException 异常信息
     * @date 2019/6/11 18:48
     */
    private static KeyStore getKeyStore(InputStream caInputStream, String cAalias)
            throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        // 证书工厂
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        // 秘钥仓库
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        keyStore.setCertificateEntry(cAalias, certificateFactory.generateCertificate(caInputStream));
        return keyStore;
    }

    private static void log(String str) {
        log.info(str);
    }
}
