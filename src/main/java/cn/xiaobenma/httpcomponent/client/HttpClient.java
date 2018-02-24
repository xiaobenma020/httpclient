package cn.xiaobenma.httpcomponent.client;

import cn.xiaobenma.httpcomponent.response.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * mailto:xiaobenma020@gmail.com
 */
public abstract class HttpClient {
    private String url;
    private RequestMethod method;
    private Map<String, Object> urlParameters;
    private Map<String, Object> urlTemplateParameters;
    private Map<String, Header> headers;
    private String requestCharset = "UTF-8";
    private String responseCharset = "UTF-8";
    private int connectTimeout = -1;
    private int readTimeout = -1;


    public HttpClient(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
        urlParameters = new LinkedHashMap<>();
        headers = new LinkedHashMap<>();
        urlTemplateParameters = new LinkedHashMap<>();
    }

    public URL getURL() {
        try {
            return new URL(getURLWithQueryString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Add URL Template parameter
     * @param name parameter name
     * @param value parameter value
     */
    public void addURLTemplateParameter(String name, Object value) {
        urlTemplateParameters.put(name, value);
    }


    /**
     * Gets URL Parameters
     *
     * @return Parameter Map
     */
    public Map<String, Object> getURLParameters() {
        return urlParameters;
    }

    /**
     * Add URL Parameter
     *
     * @param name  Parameter name
     * @param value Parameter value
     */
    public void addURLParameter(String name, Object value) {
        urlParameters.put(name, value);
    }

    /**
     * Adds multi URL Parameters
     *
     * @param urlParams
     */
    public void addURLParameters(Map<String, Object> urlParams) {
        if (null != urlParams && !urlParams.isEmpty()) {
            this.urlParameters.putAll(urlParams);
        }
    }

    /**
     * Removes URL parameter by name
     *
     * @param name Parameter name
     */
    public void removeURLParameter(String name) {
        urlParameters.remove(name);
    }

    /**
     * Adds headers
     *
     * @param headers Headers to add
     */
    public void addHeaders(Map<String, String> headers) {
        if (null != headers && !headers.isEmpty()) {
            headers.forEach(this::addHeader);
        }
    }

    /**
     * Adds header
     *
     * @param name  Header name
     * @param value Header value
     */
    public void addHeader(String name, String value) {
        if (headers.containsKey(name)) {
            headers.get(name).addValue(value);
        } else {
            headers.put(name, new Header(name, value));
        }
    }

    /**
     * Removes header by name
     *
     * @param name Header name
     */
    public void removeHeader(String name) {
        headers.remove(name);
    }

    /**
     * Gets header by name
     *
     * @param name
     * @return
     */
    public Header getHeader(String name) {
        return headers.get(name);
    }

    /**
     * Gets all headers
     *
     * @return Headers
     */
    public Header[] getAllHeaders() {
        return headers.values().toArray(new Header[headers.size()]);
    }

    /**
     * Gets request charset
     *
     * @return Request charset
     */
    public String getRequestCharset() {
        return requestCharset;
    }

    /**
     * Sets request charset
     *
     * @param requestCharset Request charset to set
     */
    public void setRequestCharset(String requestCharset) {
        this.requestCharset = requestCharset;
    }

    /**
     * Gets response charset
     *
     * @return Response charset
     */
    public String getResponseCharset() {
        return responseCharset;
    }

    /**
     * Sets response charset
     *
     * @param responseCharset Response charset
     */
    public void setResponseCharset(String responseCharset) {
        this.responseCharset = responseCharset;
    }

    private String getQueryString() {
        if (urlParameters.isEmpty()) {
            return "";
        }
        StringBuilder queryStr = new StringBuilder();
        urlParameters.forEach((key, value) -> {
            try {
                queryStr.append("&").append(key)
                        .append("=")
                        .append(null == value ? "" : URLEncoder.encode(value.toString(), requestCharset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        return queryStr.toString().substring(1);
    }

    private String getURLWithQueryString() {
        String apiURL = url;

        for (Map.Entry entry : urlTemplateParameters.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            try {
                apiURL = apiURL.replace("{" + key + "}", URLEncoder.encode(value, requestCharset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String queryStr = getQueryString();
        if (!StringUtils.isBlank(queryStr)) {
            apiURL = apiURL + (apiURL.contains("?") ? "&" : "?") + queryStr;
        }
        return apiURL;
    }

    /**
     * Check whether to output
     *
     * @return Result
     */
    abstract boolean doOutput();

    /**
     * Gets request body
     *
     * @return Request body
     */
    abstract String getRequestBody();

    /**
     * Gets Content-Type
     *
     * @return Content-Type
     */
    abstract String getContentType();

    /**
     * Gets connect timeout
     *
     * @return Connect timeout
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets connect timeout
     *
     * @param connectTimeout Connect timeout to set
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * Gets readTimeout
     *
     * @return ReadTime
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * Sets readTimeout
     *
     * @param readTimeout ReadTimeout to set
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * Gets response
     *
     * @return Response
     * @throws IOException
     */
    public Response getResponse() throws IOException {
        URL url = getURL();
        if (null == url) {
            return null;
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method.name());
        connection.setDoOutput(doOutput());
        if (connectTimeout > 0) {
            connection.setConnectTimeout(connectTimeout);
        }
        if (readTimeout > 0) {
            connection.setReadTimeout(readTimeout);
        }
        if (!headers.isEmpty()) {
            headers.values().forEach(header
                    -> connection.setRequestProperty(header.getName(), StringUtils.join(header.getValues(), ";")));
        }
        if (!StringUtils.isBlank(getContentType())) {
            connection.setRequestProperty("Content-Type", getContentType());
        }

        connection.connect();
        if (doOutput()) {
            try (OutputStream out = connection.getOutputStream()) {
                IOUtils.write(getRequestBody(), out, getRequestCharset());
            }
        }
        String contentType = connection.getHeaderField("Content-Type");
        String charset = findCharset(contentType);
        charset = StringUtils.isBlank(charset) ? getResponseCharset() : charset;

        String responseText;
        try (InputStream in = connection.getInputStream()) {
            responseText = IOUtils.toString(in, charset);
        }

        String mimeType = findMimeType(contentType);

        Map<String, List<String>> responseHeaders = connection.getHeaderFields();
        List<Header> headers = new ArrayList<>();
        responseHeaders.forEach((k, v) -> headers.add(new Header(k, v)));

        int code = connection.getResponseCode();

        connection.disconnect();

        return new Response(code, responseText, mimeType, charset, headers);

    }

    private String findCharset(String contentType) {
        if (StringUtils.isBlank(contentType)) {
            return null;
        }
        Pattern pattern = Pattern.compile("charset=([a-zA-Z0-9-]+);?");
        Matcher matcher = pattern.matcher(contentType);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String findMimeType(String contentType) {
        if (StringUtils.isBlank(contentType)) {
            return null;
        }
        Pattern pattern = Pattern.compile("([a-zA-Z0-9-]+/[a-zA-Z0-9-]+);?");
        Matcher matcher = pattern.matcher(contentType);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
