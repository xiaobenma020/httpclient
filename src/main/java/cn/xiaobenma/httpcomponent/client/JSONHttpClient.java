package cn.xiaobenma.httpcomponent.client;

import com.alibaba.fastjson.JSON;

/**
 * mailto:xiaobenma020@gmail.com
 */
public class JSONHttpClient extends AbstractParameterHttpClient {

    public JSONHttpClient(String url, RequestMethod method) {
        super(url, method);
    }

    @Override
    public String getContentType() {
        return "application/json; charset=" + getRequestCharset().toLowerCase();
    }

    @Override
    String getRequestBody() {
        if (!getParameters().isEmpty()) {
            return JSON.toJSONString(getParameters());
        }
        return "";
    }
}
