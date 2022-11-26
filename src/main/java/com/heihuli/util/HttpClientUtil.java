package com.heihuli.util;

import com.heihuli.base.CommonStringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author heihuli
 *
 * http链接工具类
 */
public class HttpClientUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 默认编码 UTF-8
     */
    private static final String DEFAULT_ENCODE = "UTF-8";

    private static RequestConfig requestConfig = RequestConfig.custom()
            // ConnectTimeout: 客户端 --> 服务端 三次握手建立时间, 超时一般是服务端网络问题、服务端关机、服务端端口问题等
            .setConnectTimeout(5000)
            // ConnectionRequestTimeout: 客户端从连接池中获取连接的超时时间, 一般连接池满了才会超时
            .setConnectionRequestTimeout(5000)
            // SocketTimeout: 单个数据包的超时时间, 不是整体数据传输的超时时间, 一般是服务端忙碌, 线程数满了, 或者服务端性能受限来不及处理请求等
            .setSocketTimeout(5000)
            .build();

    /**
     * 发送get请求
     *
     * @param url   url地址
     * @param param 参数列表
     * @return 相应数据
     * @throws Exception
     */
    public static String doGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), DEFAULT_ENCODE);
            }
        } catch (Exception e) {
            LOG.error("[HttpClientUtil] doGet request failed, errMsg: {},", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                LOG.error("[HttpClientUtil] doGet release resource failed, errMsg: {},", e);
            }
        }
        return resultString;
    }

    /**
     * 发送get请求 无参数
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * 发送post请求
     *
     * @param uri
     * @return
     * @throws Exception
     */
    public static String doPost(String uri) {
        return doPost(uri, null, null, null, true, null, null, null);
    }

    /**
     * 发送post请求<br>
     * 带请求参数
     *
     * @param uri
     * @param param
     * @return
     */
    public static String doPostParam(String uri, Map<String, String> param) {
        return doPost(uri, null, param, null, true, null, null, null);
    }

    /**
     * 发送post请求<br>
     * 带header
     *
     * @param uri
     * @param header
     * @return
     */
    public static String doPostHeader(String uri, Map<String, String> header) {
        return doPost(uri, null, null, header, true, null, null, null);
    }

    /**
     * 发送post请求<br>
     * 带请求参数<br>
     * 带header
     *
     * @param uri
     * @param param
     * @param header
     * @return
     */
    public static String doPostParamHeader(String uri, Map<String, String> param, Map<String, String> header) {
        return doPost(uri, null, param, header, true, null, null, null);
    }

    /**
     * 发送post请求<br>
     * 带表单
     *
     * @param uri
     * @param param
     * @return
     * @throws Exception
     */
    public static String doPostForm(String uri, Map<String, String> param) {
        return doPost(uri, null, param, null, true, null, null, null);
    }

    /**
     * 发送post请求<br>
     * 带表单<br>
     * 带header
     *
     * @param uri
     * @param param
     * @return
     * @throws Exception
     */
    public static String doPostFormHeader(String uri, Map<String, String> param, Map<String, String> header) {
        return doPost(uri, null, param, header, true, null, null, null);
    }

    /**
     * 发送post请求<br>
     * 带body
     *
     * @param uri
     * @param json
     * @return
     */
    public static String doPostJson(String uri, String json) {
        return doPost(uri, json, null, null, true, null, null, null);
    }

    /**
     * 发送post请求<br>
     * 带body<br>
     * 带header
     *
     * @param uri
     * @param json
     * @return
     */
    public static String doPostJsonHeader(String uri, String json, Map<String, String> header) {
        return doPost(uri, json, null, header, true, null, null, null);
    }

    /**
     * 发送post请求<br>
     * 带文件
     *
     * @param uri
     * @param fileKey
     * @param files
     * @return
     */
    public static String doPostFile(String uri, String fileKey, File ...files) {
        return doPost(uri, null, null, null, true, null, fileKey, files);
    }

    /**
     * 发送post请求<br>
     * 带输出流
     *
     * @param uri
     * @param in
     * @return
     */
    public static String doPostInputStream(String uri, InputStream in) {
        return doPost(uri, null, null, null, true, in, null, null);
    }

    /**
     * 发送post请求 底层方法
     *
     * @param uri      uri地址
     * @param json     请求体
     * @param param    请求参数
     * @param header   请求头
     * @param form     是否表单
     * @param in       输入流
     * @param fileKey  文件key 用于服务端接受@RequestParam
     * @param files    文件数组
     * @return
     * @throws Exception
     */
    public static String doPost(String uri, String json, Map<String, String> param, Map<String, String> header,
                                boolean form, InputStream in, String fileKey, File... files) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            if(!uri.toLowerCase().startsWith("http"))
                uri = "http://" + uri;
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost();
            httpPost.setConfig(requestConfig);
            URIBuilder builder = new URIBuilder(uri);

            // 添加header
            if (CommonMapUtil.isNotEmpty(header)) {
                for (String key : header.keySet()) {
                    httpPost.setHeader(key, header.get(key));
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("[HttpClientUtil] header: {}", httpPost.getAllHeaders());
                }
            }

            // 添加参数
            if (CommonMapUtil.isNotEmpty(param)) {
                List<NameValuePair> list = new ArrayList<>();
                for (String key : param.keySet()) {
                    list.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 是否表单
                if (form) {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, DEFAULT_ENCODE);
                    httpPost.setEntity(entity);
                } else {
                    // 普通参数
                    builder.setParameters(list);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("[HttpClientUtil] UrlEncodedFormEntity: {}", list);
                }
            }

            // 设置uri
            httpPost.setURI(builder.build());

            // 添加请求体
            if (CommonStringUtil.isNotBlank(json)) {
                // 创建请求内容
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                httpPost.setEntity(entity);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("[HttpClientUtil] body: {}", json);
                }
            }

            // 添加文件
            if (files != null && files.length > 0) {
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                for (int i = 0; i < files.length; i++) {
                    multipartEntityBuilder.addBinaryBody(fileKey, files[i], ContentType.DEFAULT_BINARY, URLEncoder.encode(files[i].getName(), DEFAULT_ENCODE));
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("[HttpClientUtil] file: {}, size: {}Byte", files[i].getName(), files[i].length());
                    }
                }
                HttpEntity httpEntity = multipartEntityBuilder.build();
                httpPost.setEntity(httpEntity);
            }

            // 添加流
            if (in != null) {
                httpPost.setEntity(new InputStreamEntity(in));
            }

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), DEFAULT_ENCODE);
        } catch (Exception e) {
            LOG.error("[HttpClientUtil] doPost request failed, errMsg: {},", e);
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                LOG.error("[HttpClientUtil] doPost release resource failed, errMsg: {},", e);
            }
        }
        return resultString;
    }
}
