package cn.xiaobenma.httpcomponent.client;

import org.apache.commons.lang.StringUtils;

/**
 * mailto:xiaobenma020@gmail.com
 */
public class JSONRawHttpClient extends HttpClient {

    private String jsonBody;

    public JSONRawHttpClient(String url, RequestMethod method) {
        super(url, method);
    }

    @Override
    public String getContentType() {
        return "application/json; charset=" + getRequestCharset().toLowerCase();
    }

    @Override
    String getRequestBody() {
        return StringUtils.trimToEmpty(jsonBody);
    }

    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }

    @Override
    boolean doOutput() {
        return true;
    }
}
