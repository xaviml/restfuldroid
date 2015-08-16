/*
 * Copyright (c) 2014.
 *
 * Restfullib is a library to facilitate the creation of an android app (client side) connected with server based on RESTful webservice.
 *
 * This library has been created by xaviml
 * https://github.com/xaviml
 */

package xml.restfuldroid.interfaces;


import xml.restfuldroid.enums.ConnectionErrorType;

/**
 * Restfullib is a library to facilitate the creation of an android app (client side)
 * connected with server based on RESTful webservice.
 * <p/>
 * This library has been created by xaviml
 * https://github.com/xaviml
 */
public interface OnErrorListener {

    public void onError(ConnectionErrorType connectionErrorType);
}
