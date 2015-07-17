package xml.restfuldroid.parser.simpleparser;

import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;

/**
 * Created by zenbook on 31/03/15.
 */
public class StringSimpleParser implements SimpleRequestParser<String>, SimpleResponseParser<String> {

    @Override
    public byte[] serializer(String obj) {
        return obj.getBytes();
    }

    @Override
    public String deserializer(byte[] data) {
        return new String(data);
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }
}
