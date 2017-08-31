package com.yingda.picker.model;

import java.util.List;

/**
 * Created by wenyong.hu on 2017/8/2.
 */

public class PickerModel<T> {
    private String name = "";
    private List<PickerModel> models;
    private T t;

    public PickerModel() {
    }

    public PickerModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PickerModel> getModels() {
        return models;
    }

    public void setModels(List<PickerModel> models) {
        this.models = models;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
