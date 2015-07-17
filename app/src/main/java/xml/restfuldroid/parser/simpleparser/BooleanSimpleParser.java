package xml.restfuldroid.parser.simpleparser;

import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;

/**
 * Created by zenbook on 31/03/15.
 */
public class BooleanSimpleParser implements SimpleRequestParser<Boolean>, SimpleResponseParser<Boolean> {

    @Override
    public byte[] serializer(Boolean obj) {
        return String.valueOf(obj).getBytes();
    }

    @Override
    public Boolean deserializer(byte[] data) {
        return Boolean.parseBoolean(new String(data));
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }
}
