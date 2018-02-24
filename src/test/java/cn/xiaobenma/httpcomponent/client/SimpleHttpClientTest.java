package cn.xiaobenma.httpcomponent.client;

import cn.xiaobenma.httpcomponent.response.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * mailto:xiaobenma020@gmail.com
 */
public class SimpleHttpClientTest {

    @Test
    public void testGetResponse() throws IOException {
        SimpleHttpClient client = new SimpleHttpClient("https://xxx.xx/api/user/login.do", RequestMethod.POST);
        client.setConnectTimeout(1000);
        String requestBody = "loginId=xiaobenma020@gmail.com&password=xxxxxx";
        client.setRequestBody(requestBody);
        Response response = client.getResponse();
        System.out.println("Response code:" + response.getCode());
        System.out.println("Response Content-Type:" + response.getContentType());
        System.out.println(response.getResponseText());
    }

}