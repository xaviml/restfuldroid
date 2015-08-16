package xml.restfuldroid.core.resource;

import java.lang.reflect.Proxy;

import xml.restfuldroid.core.WebService;

/**
 * Created by zenbook on 16/08/15.
 */
public class Resource {

    public static <T> Object createResource(Class<T> service, WebService webService) {
        return Proxy.newProxyInstance(
                service.getClassLoader(),
                new Class[] { service },
                new ResourceInterceptor(webService));
    }
}
