/*
 * Copyright (c) 2014.
 *
 * Restfullib is a library to facilitate the creation of an android app (client side) connected with server based on RESTful webservice.
 *
 * This library has been created by xaviml
 * https://github.com/xaviml
 */

package xml.restfuldroid.core;


import android.util.Log;

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
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;

import xml.restfuldroid.BuildConfig;
import xml.restfuldroid.CallBackUtily;
import xml.restfuldroid.core.model.Response;
import xml.restfuldroid.interfaces.OnErrorListener;
import xml.restfuldroid.parser.CustomRequestParser;
import xml.restfuldroid.parser.CustomResponseParser;
import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;

public class JsonWebService implements WebService {

    private HttpClient httpClient;

    private OnErrorListener errorListener;

    private CustomRequestParser customRequestParser;
    private CustomResponseParser customResponseParser;

    private Map<Class, Class<? extends SimpleRequestParser>> simpleRequestParsers;
    private Map<Class, Class<? extends SimpleResponseParser>> simpleResponseParsers;
    private Map<Class, SimpleRequestParser> simpleRequestParserInstances;
    private Map<Class, SimpleResponseParser> simpleResponseParserInstances;

    JsonWebService(WebServicesBuilder builder) {
        this.customRequestParser = builder.customRequestParser;
        this.customResponseParser = builder.customResponseParser;
        this.simpleRequestParsers = builder.simpleRequestParsers;
        this.simpleResponseParsers = builder.simpleResponseParsers;
        this.errorListener = builder.onErrorListener;

        this.httpClient = new DefaultHttpClient(builder.httpParams);
        this.simpleRequestParserInstances = new HashMap<>();
        this.simpleResponseParserInstances = new HashMap<>();
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

    private <T> Response<T> request(String method_name, String unformatted_url,
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

            if(simpleRequestParsers.containsKey(body.getClass())) {
                if ((p = simpleRequestParserInstances.get(body.getClass())) == null) {
                    try {
                        Class clazz = simpleRequestParsers.get(body.getClass());
                        p = (SimpleRequestParser) clazz.newInstance();
                        simpleRequestParserInstances.put(body.getClass(), p);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                requestBase.setHeader("Content-Type", p.getContentType());
                b = p.serializer(body);
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

                if(simpleResponseParsers.containsKey(cls.getClass())) {
                    if ((p = simpleResponseParserInstances.get(cls.getClass())) == null) {
                        try {
                            Class clazz = simpleResponseParsers.get(cls.getClass());
                            p = (SimpleResponseParser) clazz.newInstance();
                            simpleResponseParserInstances.put(cls.getClass(), p);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
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

    @Override
    public Response request(String method, String unformatted_url, Object... parameters) {
        return request(method, unformatted_url, null, null, parameters);
    }

    @Override
    public <T> Response<T> request(String method, Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException {
        return request(method, unformatted_url, null, cls, parameters);
    }

    @Override
    public Response get(String unformatted_url, Object... parameters) {
        return get(null, unformatted_url, parameters);
    }

    @Override
    public <T> Response<T> get(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return request("GET", unformatted_url, null, cls, parameters);
    }

    /*
     * POST METHODS
     */

    @Override
    public Response post(String unformatted_url, Object... parameters) {
        return post(null, unformatted_url, parameters);
    }

    @Override
    public <T> Response<T> post(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return post(cls, null, unformatted_url, parameters);
    }

    @Override
    public <T> Response<T> post(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return request("POST", unformatted_url, body, cls, parameters);
    }

    /*
     * PUT METHODS
     */

    @Override
    public Response put(String unformatted_url, Object... parameters) {
        return put(null, unformatted_url, parameters);
    }

    @Override
    public <T> Response<T> put(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return put(cls, null, unformatted_url, parameters);
    }

    @Override
    public <T> Response<T> put(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return request("PUT", unformatted_url, body, cls, parameters);
    }

    /*
     * PATCH METHODS
     */

    @Override
    public Response patch(String unformatted_url, Object... parameters) {
        return patch(null, unformatted_url, parameters);
    }

    @Override
    public <T> Response<T> patch(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return patch(cls, null, unformatted_url, parameters);
    }

    @Override
    public <T> Response<T> patch(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return request("PATCH", unformatted_url, body, cls, parameters);
    }

    /*
     * DELETE METHODS
     */

    @Override
    public Response delete(String unformatted_url, Object... parameters) {
        return delete(null, unformatted_url, parameters);
    }

    @Override
    public <T> Response<T> delete(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException{
        return request("DELETE", unformatted_url, null, cls, parameters);
    }
}
