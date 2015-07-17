package xml.restfuldroid.parser.simpleparser;

import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;

/**
 * Created by zenbook on 31/03/15.
 */
public class IntegerSimpleParser implements SimpleRequestParser<Integer>, SimpleResponseParser<Integer> {

    @Override
    public byte[] serializer(Integer obj) {
        return String.valueOf(obj).getBytes();
    }

    @Override
    public Integer deserializer(byte[] data) {
        return Integer.parseInt(new String(data));
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }
}
