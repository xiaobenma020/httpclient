package cn.xiaobenma020.httpcomponent.client;

import java.util.ArrayList;
import java.util.List;

/**
 * mailto:xiaobenma020@gmail.com
 */
public class Header {
    private String name;
    private List<String> values;


    public Header() {

    }

    public Header(String name, String value) {
        this.name = name;
        addValue(value);
    }

    public Header(String name, List<String> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void addValue(String value) {
        if (null == values) {
            values = new ArrayList<>();
        }
        values.add(value);
    }

    public String getValue() {
        return (null == values || values.isEmpty()) ? null : values.get(0);
    }
}
