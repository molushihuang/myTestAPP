package com.xqd.myapplication.bean;

import java.util.List;

/**
 * Created by 谢邱东 on 2019/11/28 13:52.
 * NO bug
 */
public class ResponsBase<T> extends BaseBean {

    private Boolean error;
    private List<T> results;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
