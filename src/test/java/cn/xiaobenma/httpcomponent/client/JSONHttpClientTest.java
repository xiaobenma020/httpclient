package cn.xiaobenma.httpcomponent.client;

import cn.xiaobenma.httpcomponent.response.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * mailto:xiaobenma020@gmail.com
 */
public class JSONHttpClientTest {
    @Test
    public void testGetResponse() throws IOException {
        JSONHttpClient client = new JSONHttpClient("https://xxx.xx/api/user/login.do", RequestMethod.POST);
        client.setConnectTimeout(1000);
        client.addParameter("loginId", "xiaobenma020@gmail.com");
        client.addParameter("password", "xxxxxx");
        Response response = client.getResponse();
        System.out.println(response.getCode());
        System.out.println(response.getContentType());
        System.out.println(response.getResponseText());
    }
}