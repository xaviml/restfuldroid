package xml.restfuldroid;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.google.gson.Gson;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private class TestClass {
        public int a;
        public float b;
        public boolean c;

        private TestClass(int a, float b, boolean c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    private WebService w;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        WebServicesBuilder builder = WebServicesBuilder.create();
        w = builder.build();
    }

    //TODO: test for: get, post, put, patch and delete combined with: Integer, Float, Boolean, Integer,
    //TODO: String, Double, JSONParser. Serializer and Deserializer
    public void testGetString() {
        String out = "{\n" +
                "  \"img\":\"http://www.mricons.com/store/png/34551_11918_tux_64x64.png\",\n" +
                "  \"string\": \"Test\"\n" +
                "}";
        String url = "https://gist.githubusercontent.com/xaviml/f2e738129c2f79b3b29d/raw/734c631a2f59c8908673576d41086e34c7747139/testimage.json";
        Response<String> r = w.get(String.class, url);
        assertEquals(r.data, out);
    }

    public void testGetInteger() {
        Integer out = 134;
        String url = "https://gist.githubusercontent.com/xaviml/9ffd4f57392d9606c5a3/raw/fa59ff276dba81ce358e8dfb002082053d6686cd/testinteger";
        Response<Integer> r = w.get(Integer.class, url);
        assertEquals(r.data, out);
    }

    public void testGetBoolean() {
        Boolean out = false;
        String url = "https://gist.githubusercontent.com/xaviml/6448554ff7d4e97ceedb/raw/02e4a84d62c4b0fe9cca60bba7b9799f78f1f7ed/testboolean";
        Response<Boolean> r = w.get(Boolean.class, url);
        assertEquals(r.data, out);
    }
    /*
    public void testGetBitmap() {
        byte[] data = "".getBytes();
        Bitmap out = BitmapFactory.decodeByteArray(data, 0,
                data.length);
        String url = "http://www.mricons.com/store/png/34551_11918_tux_64x64.png";
        Response<Bitmap> r = w.get(Bitmap.class, url);
        assertEquals(r.data, out);
    }

    public void testPostWithStringBodyAndStringResponse() {

    }
    */
}