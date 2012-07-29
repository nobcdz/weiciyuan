package org.qii.weiciyuan.support.http;


import ch.boye.httpclientandroidlib.*;
import ch.boye.httpclientandroidlib.client.CookieStore;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.client.protocol.ClientContext;
import ch.boye.httpclientandroidlib.client.utils.URIBuilder;
import ch.boye.httpclientandroidlib.impl.client.BasicCookieStore;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.params.BasicHttpParams;
import ch.boye.httpclientandroidlib.params.CoreProtocolPNames;
import ch.boye.httpclientandroidlib.params.HttpParams;
import ch.boye.httpclientandroidlib.protocol.BasicHttpContext;
import ch.boye.httpclientandroidlib.protocol.HttpContext;
import ch.boye.httpclientandroidlib.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: qii
 * Date: 12-7-29
 * Time: 上午10:10
 * To change this template use File | Settings | File Templates.
 */
public class HttpUtility {

    private static HttpUtility httpUtility = new HttpUtility();
    private HttpClient httpclient = null;
    private HttpGet httpGet = new HttpGet();
    private HttpPost httpPost = new HttpPost();

    private HttpUtility() {

        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        httpclient = new DefaultHttpClient(params);


    }

    public static HttpUtility getInstance() {

        return httpUtility;
    }


    public String execute(HttpMethod httpMethod, String url, Map<String, String> param) {
        switch (httpMethod) {
            case Post:
                return doPost(url, param);
            case Get:
                try {
                    return doGet(url, param);
                } catch (Exception e) {

                }
        }
        return "";
    }

    public String doPost(String url, Map<String, String> param) {


        return "";
    }

    public String doGet(String url, Map<String, String> param) throws URISyntaxException, IOException {

        List<NameValuePair> qparams = new ArrayList<NameValuePair>();

        URIBuilder uriBuilder = new URIBuilder(url);


        Set<String> keys = param.keySet();

        for (String key : keys) {

            uriBuilder.addParameter(key, param.get(key));
        }

        httpGet.setURI(uriBuilder.build());

        CookieStore cookieStore = new BasicCookieStore();

        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);


        HttpResponse response = httpclient.execute(httpGet, localContext);

        return dealWithResponse(response);

    }

    private String dealWithResponse(HttpResponse httpResponse) {


        StatusLine status = httpResponse.getStatusLine();
        int statusCode = status.getStatusCode();

        String result = "";

        if (statusCode != 200) {
            dealWithError(httpResponse);
        }


        result = readResult(httpResponse);


        return result;

    }

    private String readResult(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String result = "";

        try {
            result = EntityUtils.toString(entity);

        } catch (IOException e) {

        }
        return result;
    }

    private String dealWithError(HttpResponse httpResponse) {

        StatusLine status = httpResponse.getStatusLine();
        int statusCode = status.getStatusCode();

        String result = "";

        if (statusCode != 200) {

            result = readResult(httpResponse);
            String err = null;
            int errCode = 0;
            try {
                JSONObject json = new JSONObject(result);
                err = json.getString("error");
                errCode = json.getInt("error_code");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return result;
    }


    private void dealCookie() {
//        List<Cookie> cookies = cookieStore.getCookies();
//              for (int i = 0; i < cookies.size(); i++) {
//                  System.out.println("Local cookie: " + cookies.get(i));
//              }
    }
}
