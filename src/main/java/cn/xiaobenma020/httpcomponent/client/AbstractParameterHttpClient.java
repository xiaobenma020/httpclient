package cn.xiaobenma020.httpcomponent.client;

import java.util.HashMap;
import java.util.Map;

/**
 * mailto:xiaobenma020@gmail.com
 */
abstract class AbstractParameterHttpClient extends HttpClient {

    private Map<String, Object> parameters;

    public AbstractParameterHttpClient(String url, RequestMethod method) {
        super(url, method);
        parameters = new HashMap<>();
    }


    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void addParameter(String name, Object value) {
        parameters.put(name, value);
    }

    public void addParameters(Map<String, Object> parameters) {
        if (null != parameters && !parameters.isEmpty()) {
            this.parameters.putAll(parameters);
        }
    }

    public void removeParameter(String name) {
        parameters.remove(name);
    }

    @Override
    boolean doOutput() {
        return !parameters.isEmpty();
    }
}
