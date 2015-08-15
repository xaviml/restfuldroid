package xml.restfuldroid.parser.customparser;

import java.lang.reflect.Field;

import xml.restfuldroid.parser.CustomRequestParser;

/**
 * Created by zenbook on 10/07/15.
 */
public class FormDataCustomParser implements CustomRequestParser {
    @Override
    public <T> byte[] serializer(T obj) {
        Class c = obj.getClass();
        Field[] fields = c.getDeclaredFields();
        StringBuilder out = new StringBuilder("");
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            out.append(fields[i].getName());
            out.append("=");
            try {
                out.append(fields[i].get(obj).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (i != fields.length - 1) {
                out.append("&");
            }
        }

        return out.toString().getBytes();
    }

    @Override
    public String getContentType() {
        return "application/x-www-form-urlencoded";
    }

    public static void main(String[] args) {
        FormDataCustomParser formDataCustomParser = new FormDataCustomParser();
        MyClass myClass = new MyClass();
        formDataCustomParser.serializer(myClass);
    }

    public static class MyClass {
        public String user;
        public int count;
    }
}
