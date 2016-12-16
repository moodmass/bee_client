package com.hyzc.bee.client.layout.store;

import java.io.Serializable;
import java.util.List;

public class Store implements Serializable {
    private String code;
    private String name;
    private List<Device> deviceList;

    public static Store getDefaultStore() {
        Store store = new Store();
        store.setCode("-1");
        store.setName("全部店铺");
        return store;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }


    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

}


