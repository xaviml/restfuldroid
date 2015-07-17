package xml.restfuldroid.parser;

/**
 * Created by zenbook on 30/03/15.
 */
public interface SimpleResponseParser<T> {
    public T deserializer(byte[] data);

}
