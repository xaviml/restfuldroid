package xml.restfuldroid.parser.simpleparser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

import xml.restfuldroid.parser.SimpleRequestParser;
import xml.restfuldroid.parser.SimpleResponseParser;

/**
 * Created by zenbook on 31/03/15.
 */
public class BitmapSimpleParser implements SimpleRequestParser<Bitmap>, SimpleResponseParser<Bitmap> {

    @Override
    public byte[] serializer(Bitmap obj) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        obj.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public Bitmap deserializer(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0,
                data.length);
    }

    @Override
    public String getContentType() {
        return "image";
    }
}
