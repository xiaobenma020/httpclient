package cn.xiaobenma020.httpcomponent.client;

import cn.xiaobenma020.httpcomponent.response.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * mailto:xiaobenma020@gmail.com
 */
public class FormURLEncodedHttpClientTest {

    @Test
    public void testGetResponse() throws IOException {
        FormURLEncodedHttpClient client = new FormURLEncodedHttpClient("https://xxx.xx/api/user/login.do"
                , RequestMethod.POST);
        client.setConnectTimeout(1000);
        client.addParameter("loginId", "xiaobenma020@gmail.com");
        client.addParameter("password", "xxxxxx");
        Response response = client.getResponse();
        System.out.println("Response code:" + response.getCode());
        System.out.println("Response Content-Type:" + response.getContentType());
        System.out.println(response.getResponseText());
    }

}