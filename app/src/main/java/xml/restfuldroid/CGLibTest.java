package xml.restfuldroid;

import java.util.List;

import xml.restfuldroid.annotation.Service;
import xml.restfuldroid.core.WebService;
import xml.restfuldroid.core.model.Response;
import xml.restfuldroid.core.resource.Resource;
import xml.restfuldroid.parser.customparser.FormDataCustomParser;
import xml.restfuldroid.test.WebServiceStub;

public class CGLibTest
{

    public class MyUser {
        public String user;
        public String email;
        public boolean isActivated;
    }

    @xml.restfuldroid.annotation.Resource(value="http://demo0699944.mockable.io/user")
    public interface MyService {

        @Service(value="/test", method="GET")
        int test();

        @Service(value="/", method="GET")
        Response<List<MyUser>> findActiveUsers();

        @Service(value="/isActivated", method="GET")
        Response<FormDataCustomParser.MyClass> exists();

        @Service(value="validate", method="POST")
        void validate();
    }

    public static void main(String... args)
    {
        //WebServicesBuilder builder = WebServicesBuilder.create();
        WebService w = new WebServiceStub();
        //WebService w = builder.build();

        MyService myService = (MyService) Resource.createResource(MyService.class, w);
        System.out.println("Value from instance: " + myService.findActiveUsers());
        System.out.println();
        System.out.println("Value from instance: " + myService.exists());
        System.out.println();
    }
}
