package xml.restfuldroid.parser;

/**
 * Created by zenbook on 31/03/15.
 */
public interface SimpleRequestParser<T> {
    public byte[] serializer(T obj);
    public String getContentType();
}
