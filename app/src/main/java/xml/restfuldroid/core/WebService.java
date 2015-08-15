package xml.restfuldroid.core;

import java.util.IllegalFormatException;

import xml.restfuldroid.core.model.Response;

/**
 * Created by zenbook on 9/07/15.
 */
public interface WebService {
    Response get(String unformatted_url, Object... parameters);

    <T> Response<T> get(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException;

    Response post(String unformatted_url, Object... parameters);

    <T> Response<T> post(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException;

    <T> Response<T> post(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException;

    Response put(String unformatted_url, Object... parameters);

    <T> Response<T> put(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException;

    <T> Response<T> put(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException;

    Response patch(String unformatted_url, Object... parameters);

    <T> Response<T> patch(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException;

    <T> Response<T> patch(Class<T> cls, Object body, String unformatted_url, Object... parameters) throws IllegalFormatException;

    Response delete(String unformatted_url, Object... parameters);

    <T> Response<T> delete(Class<T> cls, String unformatted_url, Object... parameters) throws IllegalFormatException;
}
