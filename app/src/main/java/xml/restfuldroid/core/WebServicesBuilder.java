/*
 * Copyright (c) 2014.
 *
 * Restfullib is a library to facilitate the creation of an android app (client side) connected with server based on RESTful webservice.
 *
 * This library has been created by xaviml
 * https://github.com/xaviml
 */

package xml.restfuldroid.core;


import android.graphics.Bitmap;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.HashMap;
import java.util.Map;

import xml.restfuldroid.interfaces.OnErrorListener;
import xml.restfuldroid.parser.CustomRequestParser;
import xml.restfuldroid.parser.CustomResponseParser;
import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;
import xml.restfuldroid.parser.WebServiceParser;
import xml.restfuldroid.parser.customparser.JSONCustomParser;
import xml.restfuldroid.parser.simpleparser.BitmapSimpleParser;
import xml.restfuldroid.parser.simpleparser.BooleanSimpleParser;
import xml.restfuldroid.parser.simpleparser.DoubleSimpleParser;
import xml.restfuldroid.parser.simpleparser.FloatSimpleParser;
import xml.restfuldroid.parser.simpleparser.IntegerSimpleParser;
import xml.restfuldroid.parser.simpleparser.StringSimpleParser;


public class WebServicesBuilder {
    public static final CustomRequestParser DEFAULT_REQUEST_PARSER = new JSONCustomParser();
    public static final CustomResponseParser DEFAULT_RESPONSE_PARSER = (CustomResponseParser) DEFAULT_REQUEST_PARSER;
    public static final OnErrorListener DEFAULT_ERROR_LISTENER = null;
    public static final HttpParams DEFAULT_HTTP_PARAMS = getDefaultHttpParams();
    public static final ResponseView DEFAULT_RESPONSE_VIEW = ResponseView.JSON;
    private static HttpParams getDefaultHttpParams() {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
        HttpConnectionParams.setSoTimeout(httpParameters, 30000);
        return httpParameters;
    }

    public enum ResponseView {
        JSON, HTML, XML
    }

    CustomRequestParser customRequestParser = DEFAULT_REQUEST_PARSER;
    CustomResponseParser customResponseParser = DEFAULT_RESPONSE_PARSER;
    Map<Class, Class<? extends SimpleRequestParser>> simpleRequestParsers;
    Map<Class, Class<? extends SimpleResponseParser>> simpleResponseParsers;
    OnErrorListener onErrorListener = DEFAULT_ERROR_LISTENER;
    HttpParams httpParams = DEFAULT_HTTP_PARAMS;
    ResponseView responseView = DEFAULT_RESPONSE_VIEW;

    private WebServicesBuilder() {

        simpleRequestParsers = new HashMap<>();
        simpleRequestParsers.put(Integer.class, IntegerSimpleParser.class);
        simpleRequestParsers.put(Float.class, FloatSimpleParser.class);
        simpleRequestParsers.put(Double.class, DoubleSimpleParser.class);
        simpleRequestParsers.put(Boolean.class, BooleanSimpleParser.class);
        simpleRequestParsers.put(String.class, StringSimpleParser.class);
        simpleRequestParsers.put(Bitmap.class, BitmapSimpleParser.class);

        simpleResponseParsers = new HashMap<>();
        simpleResponseParsers.put(Integer.class, IntegerSimpleParser.class);
        simpleResponseParsers.put(Float.class, FloatSimpleParser.class);
        simpleResponseParsers.put(Double.class, DoubleSimpleParser.class);
        simpleResponseParsers.put(Boolean.class, BooleanSimpleParser.class);
        simpleResponseParsers.put(String.class, StringSimpleParser.class);
        simpleResponseParsers.put(Bitmap.class, BitmapSimpleParser.class);
    }

    public static WebServicesBuilder create() {
        return new WebServicesBuilder();
    }

    public WebServicesBuilder setCustomRequestParser(CustomRequestParser customRequestParser) {
        this.customRequestParser = customRequestParser;
        return this;
    }

    public WebServicesBuilder setCustomResponseParser(CustomResponseParser customResponseParser) {
        this.customResponseParser = customResponseParser;
        return this;
    }

    public WebServicesBuilder registerSimpleRequestParser(Class<?> c, Class<? extends SimpleRequestParser> s) {
        simpleRequestParsers.put(c,s);
        return this;
    }

    public WebServicesBuilder registerSimpleResponseParser(Class<?> c, Class<? extends SimpleResponseParser> s) {
        simpleResponseParsers.put(c,s);
        return this;
    }

    public WebServicesBuilder setOnErrorListener(OnErrorListener listener) {
        this.onErrorListener = listener;
        return this;
    }

    public WebServicesBuilder setHttpParams(HttpParams httpParams) {
        this.httpParams = httpParams;
        return this;
    }

    public WebService build() {
        WebService w;
        switch (responseView) {
            case JSON:
                w = new JsonWebService(this);
                break;
            default:
                w = new JsonWebService(this);
                break;
        }


        if(customRequestParser instanceof WebServiceParser) {
            ((WebServiceParser) customRequestParser).initWebService(w);
        }

        if(customResponseParser instanceof WebServiceParser) {
            ((WebServiceParser) customResponseParser).initWebService(w);
        }

        return w;
    }
}
