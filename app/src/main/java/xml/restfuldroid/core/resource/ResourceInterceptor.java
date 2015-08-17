package xml.restfuldroid.core.resource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import xml.restfuldroid.annotation.Resource;
import xml.restfuldroid.annotation.Service;
import xml.restfuldroid.core.WebService;
import xml.restfuldroid.core.model.Response;
import xml.restfuldroid.exception.ResourceException;

/**
 * Created by zenbook on 16/08/15.
 */
public class ResourceInterceptor implements InvocationHandler {

    private final WebService service;

    public ResourceInterceptor(WebService service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {

        String url = "";
        String httpMethod;
        Class<?> responseClazz;

        Resource resource = method.getDeclaringClass().getAnnotation(Resource.class);
        if (resource != null)
            url += resource.value();

        //System.out.println("Method annotations");
        Service service = method.getAnnotation(Service.class);
        if (service != null) {
            url += service.value();
            httpMethod = service.method();
            responseClazz = service.response();
        }else
            throw new ResourceException("Service annotation not found in: " + method.getDeclaringClass().getName());

        /*
        System.out.println("Parameter annotations");
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            for (int j = 0; j < annotations.length; i++) {
                System.out.println(annotations[j].annotationType().getName());
            }
        }*/


        if(method.getReturnType() == void.class) {
            this.service.request(httpMethod, url);
            return null;
        }else if(method.getReturnType() == Response.class) {
            return this.service.request(httpMethod, responseClazz, url);
        }else {
            return this.service.request(httpMethod, responseClazz, url).data;
        }
    }
}
