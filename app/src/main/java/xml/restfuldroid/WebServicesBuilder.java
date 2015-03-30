/*
 * Copyright (c) 2014.
 *
 * Restfullib is a library to facilitate the creation of an android app (client side) connected with server based on RESTful webservice.
 *
 * This library has been created by xaviml
 * https://github.com/xaviml
 */

package xml.restfuldroid;


import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import xml.restfuldroid.interfaces.OnErrorListener;
import xml.restfuldroid.interfaces.OnImageListener;


public class WebServicesBuilder {
    public static final OnErrorListener DEFAULT_ERROR_LISTENER = null;
    public static final HttpParams DEFAULT_HTTP_PARAMS = getDefaultHttpParams();
    private static final HttpParams getDefaultHttpParams() {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
        HttpConnectionParams.setSoTimeout(httpParameters, 30000);
        return httpParameters;
    }
    public static final OnImageListener DEFAULT_IMAGE_LISTENER = null;


    private OnErrorListener onErrorListener = DEFAULT_ERROR_LISTENER;
    private HttpParams httpParams = DEFAULT_HTTP_PARAMS;
    private OnImageListener onImageListener = DEFAULT_IMAGE_LISTENER;

    WebServicesBuilder() { }
    public static final WebServicesBuilder create() {
        return new WebServicesBuilder();
    }

    public WebServicesBuilder setOnErrorListener(OnErrorListener listener) {
        this.onErrorListener = listener;
        return this;
    }

    public WebServicesBuilder setHttpParams(HttpParams httpParams) {
        this.httpParams = httpParams;
        return this;
    }

    public WebServicesBuilder downloadImagesOnParallel(OnImageListener onImageListener) {
        this.onImageListener = onImageListener;
        return this;
    }

    public WebServicesBuilder downloadImagesOnSameThread() {
        this.onImageListener = null;
        return this;
    }

    public WebService build() {
        return new WebService(this.onErrorListener,
                this.httpParams,
                this.onImageListener);
    }
}
