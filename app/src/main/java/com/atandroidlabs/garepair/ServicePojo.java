package com.atandroidlabs.garepair;


public class ServicePojo {
    private String serviceName;
    private String warrenty;
    private String duration;
    private int price;
    private int resource;

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(String warrenty) {
        this.warrenty = warrenty;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
