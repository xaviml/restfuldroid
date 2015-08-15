package xml.restfuldroid.parser;

/**
 * Created by zenbook on 30/03/15.
 */
public interface CustomRequestParser {
    <T> byte[] serializer(T obj);
    String getContentType();
}
