package cn.xiaobenma020.httpcomponent.response;


import cn.xiaobenma020.httpcomponent.client.Header;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * mailto:xiaobenma020@gmail.com
 */
public class Response {

    private int code;
    private String responseText;
    private String contentType;
    private String contentEncoding;

    private Map<String, Header> headers;


    public Response(int code, String responseText, String mimeType
            , String charset, List<Header> headers) {
        this.code = code;
        this.responseText = getNotNullString(responseText);
        this.contentType = getNotNullString(mimeType);
        this.contentEncoding = getNotNullString(charset);
        this.headers = new LinkedHashMap<>();
        addHeaders(headers);
    }

    private void addHeaders(List<Header> headers) {
        if (null != headers) {
            for (Header header : headers) {
                this.headers.put(header.getName(), header);
            }
        }
    }

    private String getNotNullString(String str) {
        return null == str ? "" : str;
    }

    private Header[] getNotNullHeaders(Header[] headers) {
        return null == headers ? new Header[0] : headers;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = getNotNullString(responseText);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = getNotNullString(contentType);
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = getNotNullString(contentEncoding);
    }

    public Header getHeader(String name) {
        return headers.get(name);
    }

    public Header[] getAllHeaders() {
        return headers.values().toArray(new Header[headers.size()]);
    }
}
