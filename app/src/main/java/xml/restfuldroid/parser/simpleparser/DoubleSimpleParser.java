package xml.restfuldroid.parser.simpleparser;

import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;

/**
 * Created by zenbook on 31/03/15.
 */
public class DoubleSimpleParser implements SimpleRequestParser<Double>, SimpleResponseParser<Double> {

    @Override
    public byte[] serializer(Double obj) {
        return String.valueOf(obj).getBytes();
    }

    @Override
    public Double deserializer(byte[] data) {
        return Double.parseDouble(new String(data));
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }
}
