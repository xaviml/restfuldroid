package xml.restfuldroid.test;

import java.util.IllegalFormatException;

import xml.restfuldroid.core.WebService;
import xml.restfuldroid.core.model.Response;

/**
 * Created by zenbook on 16/08/15.
 */
public class WebServiceStub implements WebService{
    @Override
    public Response request(String method, String unformatted_url, Object... parameters) {
        return new Response();
    }

    @Override
    public <T> Response<T> request(String method, Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException {
        return new Response<>();
    }

    @Override
    public Response get(String unformatted_url, Object... parameters) {
        return new Response();
    }

    @Override
    public <T> Response<T> get(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException {
        return new Response<>();
    }

    @Override
    public Response post(String unformatted_url, Object... parameters) {
        return new Response<>();
    }

    @Override
    public <T> Response<T> post(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException {
        return new Response<>();
    }

    @Override
    public <T> Response<T> post(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException {
        return new Response<>();
    }

    @Override
    public Response put(String unformatted_url, Object... parameters) {
        return new Response<>();
    }

    @Override
    public <T> Response<T> put(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException {
        return new Response<>();
    }

    @Override
    public <T> Response<T> put(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException {
        return new Response<>();
    }

    @Override
    public Response patch(String unformatted_url, Object... parameters) {
        return new Response<>();
    }

    @Override
    public <T> Response<T> patch(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException {
        return new Response<>();
    }

    @Override
    public <T> Response<T> patch(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException {
        return new Response<>();
    }

    @Override
    public Response delete(String unformatted_url, Object... parameters) {
        return new Response<>();
    }

    @Override
    public <T> Response<T> delete(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException {
        return new Response<>();
    }
}
