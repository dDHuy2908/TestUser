package com.ddhuy4298.test.models;

import java.io.Serializable;

public class Service implements Serializable {
    private Integer image;
    private String service;

    public Service(Integer image, String service) {
        this.image = image;
        this.service = service;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
