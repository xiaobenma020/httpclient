package cn.xiaobenma020.httpcomponent.client;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * mailto:xiaobenma020@gmail.com
 */
public class FormURLEncodedHttpClient extends AbstractParameterHttpClient {


    public FormURLEncodedHttpClient(String url, RequestMethod method) {
        super(url, method);
    }

    @Override
    String getRequestBody() {
        if (!getParameters().isEmpty()) {
            StringBuilder body = new StringBuilder();
            getParameters().forEach((key, value) -> {
                try {
                    body.append(key)
                            .append("=")
                            .append(URLEncoder.encode(null == value ? "" : value.toString(), getRequestCharset()))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
            body.deleteCharAt(body.length() - 1);
            return body.toString();
        }
        return "";
    }

    @Override
    public String getContentType() {
        return "application/x-www-form-urlencoded; charset=" + getRequestCharset().toLowerCase();
    }
}
