/*
 * Copyright (c) 2014.
 *
 * Restfullib is a library to facilitate the creation of an android app (client side) connected with server based on RESTful webservice.
 *
 * This library has been created by xaviml
 * https://github.com/xaviml
 */

package xml.restfuldroid;


//import android.support.v4.app.Fragment;

import xml.restfuldroid.enums.ConnectionErrorType;
import xml.restfuldroid.interfaces.OnErrorListener;
import xml.restfuldroid.exception.ConnectionErrorException;


public class CallBackUtily {

    private CallBackUtily() { }


    public static void callbackToUser(Object obj, final Runnable runnable) {
        /*
        if(obj instanceof Activity) {
            ((Activity) obj).runOnUiThread(runnable);
        } else if(obj instanceof Fragment) {
            ((Fragment)obj).getActivity().runOnUiThread(runnable);
        } else
            runnable.run();
            */
    }

    public static void callbackIOException(final OnErrorListener listener) {
        if(listener != null) {
            CallBackUtily.callbackToUser(listener, new Runnable() {
                @Override
                public void run() {
                    listener.onError(ConnectionErrorType.NO_INTERNET_CONNECTION);
                }
            });
        }else
            throw new ConnectionErrorException("Problem with internet connection. If you want control this," +
                    "you must implements OnErrorListener and include it on WebServiceConfig");
    }
}
