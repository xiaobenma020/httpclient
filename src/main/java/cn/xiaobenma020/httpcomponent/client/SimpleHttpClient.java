package cn.xiaobenma020.httpcomponent.client;

import org.apache.commons.lang.StringUtils;

/**
 * mailto:xiaobenma020@gmail.com
 */
public class SimpleHttpClient extends HttpClient {

    private String requestBody;
    private String contentType;

    public SimpleHttpClient(String url, RequestMethod method) {
        super(url, method);
    }

    @Override
    boolean doOutput() {
        return !StringUtils.isBlank(requestBody);
    }

    @Override
    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
