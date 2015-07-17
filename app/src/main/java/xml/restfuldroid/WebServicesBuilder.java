/*
 * Copyright (c) 2014.
 *
 * Restfullib is a library to facilitate the creation of an android app (client side) connected with server based on RESTful webservice.
 *
 * This library has been created by xaviml
 * https://github.com/xaviml
 */

package xml.restfuldroid;


import android.graphics.Bitmap;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.HashMap;

import xml.restfuldroid.interfaces.OnErrorListener;
import xml.restfuldroid.interfaces.OnImageListener;
import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;
import xml.restfuldroid.parser.json.JSONCustomParser;
import xml.restfuldroid.parser.CustomRequestParser;
import xml.restfuldroid.parser.CustomResponseParser;
import xml.restfuldroid.parser.WebServiceParser;
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
    private static HttpParams getDefaultHttpParams() {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
        HttpConnectionParams.setSoTimeout(httpParameters, 30000);
        return httpParameters;
    }

    private CustomRequestParser customRequestParser = DEFAULT_REQUEST_PARSER;
    private CustomResponseParser customResponseParser = DEFAULT_RESPONSE_PARSER;
    private HashMap<Class, SimpleRequestParser> simpleRequestParsers;
    private HashMap<Class, SimpleResponseParser> simpleResponseParsers;
    private OnErrorListener onErrorListener = DEFAULT_ERROR_LISTENER;
    private HttpParams httpParams = DEFAULT_HTTP_PARAMS;

    WebServicesBuilder() {
        IntegerSimpleParser integerSimpleParser =  new IntegerSimpleParser();
        FloatSimpleParser floatSimpleParser = new FloatSimpleParser();
        DoubleSimpleParser doubleSimpleParser = new DoubleSimpleParser();
        BooleanSimpleParser booleanSimpleParser = new BooleanSimpleParser();
        StringSimpleParser stringSimpleParser = new StringSimpleParser();
        BitmapSimpleParser bitmapSimpleParser = new BitmapSimpleParser();

        simpleRequestParsers = new HashMap<>();
        simpleRequestParsers.put(Integer.class, integerSimpleParser);
        simpleRequestParsers.put(Float.class, floatSimpleParser);
        simpleRequestParsers.put(Double.class, doubleSimpleParser);
        simpleRequestParsers.put(Boolean.class, booleanSimpleParser);
        simpleRequestParsers.put(String.class, stringSimpleParser);
        simpleRequestParsers.put(Bitmap.class, bitmapSimpleParser);

        simpleResponseParsers = new HashMap<>();
        simpleResponseParsers.put(Integer.class, integerSimpleParser);
        simpleResponseParsers.put(Float.class, floatSimpleParser);
        simpleResponseParsers.put(Double.class, doubleSimpleParser);
        simpleResponseParsers.put(Boolean.class, booleanSimpleParser);
        simpleResponseParsers.put(String.class, stringSimpleParser);
        simpleResponseParsers.put(Bitmap.class, bitmapSimpleParser);
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

    public WebServicesBuilder registerSimpleRequestParser(Class<?> c, SimpleRequestParser s) {
        simpleRequestParsers.put(c,s);
        return this;
    }

    public WebServicesBuilder registerSimpleResponseParser(Class<?> c, SimpleResponseParser s) {
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
        WebService w = new WebService(
                this.customRequestParser, this.customResponseParser,
                this.simpleRequestParsers, this.simpleResponseParsers,
                this.onErrorListener, this.httpParams);

        if(customRequestParser instanceof WebServiceParser) {
            ((WebServiceParser) customRequestParser).initWebService(w);
        }

        if(customResponseParser instanceof WebServiceParser) {
            ((WebServiceParser) customResponseParser).initWebService(w);
        }

        return w;
    }
}
