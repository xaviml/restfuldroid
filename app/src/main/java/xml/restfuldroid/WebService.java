/*
 * Copyright (c) 2014.
 *
 * Restfullib is a library to facilitate the creation of an android app (client side) connected with server based on RESTful webservice.
 *
 * This library has been created by xaviml
 * https://github.com/xaviml
 */

package xml.restfuldroid;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.IllegalFormatException;

import xml.restfuldroid.enums.DataType;
import xml.restfuldroid.interfaces.OnErrorListener;
import xml.restfuldroid.interfaces.OnImageListener;
import xml.restfuldroid.parser.CustomRequestParser;
import xml.restfuldroid.parser.CustomResponseParser;
import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;

public class WebService {

    private HttpClient httpClient;

    private OnErrorListener errorListener;

    private CustomRequestParser customRequestParser;
    private CustomResponseParser customResponseParser;
    private HashMap<Class, SimpleRequestParser> simpleRequestParsers;
    private HashMap<Class, SimpleResponseParser> simpleResponseParsers;

    WebService (CustomRequestParser customRequestParser,
                CustomResponseParser customResponseParser,
                HashMap<Class, SimpleRequestParser> simpleRequestParsers,
                HashMap<Class, SimpleResponseParser> simpleResponseParsers,
                OnErrorListener errorListener,
                HttpParams httpParameters) {
        this.customRequestParser = customRequestParser;
        this.customResponseParser = customResponseParser;
        this.simpleRequestParsers = simpleRequestParsers;
        this.simpleResponseParsers = simpleResponseParsers;
        this.errorListener = errorListener;

        this.httpClient = new DefaultHttpClient(httpParameters);
    }

    private HttpRequestBase getHttpRequestBase(String url, String method_name) {
        switch (method_name) {
            case "GET":
                return new HttpGet(url);
            case "POST":
                return new HttpPost(url);
            case "PUT":
                return new HttpPut(url);
            case "PATCH":
                return new HttpPatch(url);
            case "DELETE":
                return new HttpDelete(url);
            default:
                return null;
        }
    }

    private <T> Response<T> request(String unformatted_url, String method_name,
                                    Object body, Class<T> cls, Object... parameters)
                                    throws IllegalFormatException {
        if (body != null && (method_name.equals("GET") || method_name.equals("DELETE")) && BuildConfig.DEBUG)
            throw new AssertionError();
        HttpRequestBase requestBase = getHttpRequestBase(String.format(unformatted_url,parameters), method_name);
        assert requestBase != null;
        Response<T> response = new Response<>();

        //If there are body request...
        if(body != null) {
            SimpleRequestParser p;
            byte[] b;
            if((p = simpleRequestParsers.get(body.getClass()))!=null) {
                b = p.serializer(body);
                requestBase.setHeader("Content-Type", p.getContentType());
            } else{
                b = customRequestParser.serializer(body);
                requestBase.setHeader("Content-Type", customRequestParser.getContentType());
            }

            HttpEntityEnclosingRequestBase tmp = (HttpEntityEnclosingRequestBase) requestBase;
            tmp.setEntity(new ByteArrayEntity(b));
        }

        try {
            HttpResponse httpResponse = httpClient.execute(requestBase);
            //TODO: control the status code or not?
            response.status = httpResponse.getStatusLine().getStatusCode();
            if(cls != null) {
                byte[] body_response = EntityUtils.toByteArray(httpResponse.getEntity());
                SimpleResponseParser p;
                if((p = simpleResponseParsers.get(cls))!=null) {
                    //TODO: This line is correct?
                    response.data = (T) p.deserializer(body_response);
                } else{
                    response.data = customResponseParser.deserializer(body_response, cls);
                }
            } else
                response.data = null;
            return response;
        } catch (IOException e) {
            //TODO: should I do this?
            CallBackUtily.callbackIOException(errorListener);
            return null;
        }
    }

    /*
     * GET METHODS
     */

    public Response get(String unformatted_url, Object... parameters) {
        return get(null, unformatted_url, parameters);
    }

    public <T> Response<T> get(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return request(unformatted_url, "GET", null, cls, parameters);
    }

    /*
     * POST METHODS
     */

    public Response post(String unformatted_url, Object... parameters) {
        return post(null, unformatted_url, parameters);
    }

    public <T> Response<T> post(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return post(cls, null, unformatted_url, parameters);
    }

    public <T> Response<T> post(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return request(unformatted_url, "POST", body, cls, parameters);
    }

    /*
     * PUT METHODS
     */

    public Response put(String unformatted_url, Object... parameters) {
        return put(null, unformatted_url, parameters);
    }

    public <T> Response<T> put(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return put(cls, null, unformatted_url, parameters);
    }

    public <T> Response<T> put(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return request(unformatted_url, "PUT", body, cls, parameters);
    }

    /*
     * PATCH METHODS
     */

    public Response patch(String unformatted_url, Object... parameters) {
        return patch(null, unformatted_url, parameters);
    }

    public <T> Response<T> patch(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return patch(cls, null, unformatted_url, parameters);
    }

    public <T> Response<T> patch(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return request(unformatted_url, "PATCH", body, cls, parameters);
    }

    /*
     * DELETE METHODS
     */

    public Response delete(String unformatted_url, Object... parameters) {
        return delete(null, unformatted_url, parameters);
    }

    public <T> Response<T> delete(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return request(unformatted_url, "DELETE", null, cls, parameters);
    }
}
