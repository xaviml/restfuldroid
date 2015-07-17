package xml.restfuldroid.parser.simpleparser;

import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;

/**
 * Created by zenbook on 31/03/15.
 */
public class FloatSimpleParser implements SimpleRequestParser<Float>, SimpleResponseParser<Float> {

    @Override
    public byte[] serializer(Float obj) {
        return String.valueOf(obj).getBytes();
    }

    @Override
    public Float deserializer(byte[] data) {
        return Float.parseFloat(new String(data));
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }
}
